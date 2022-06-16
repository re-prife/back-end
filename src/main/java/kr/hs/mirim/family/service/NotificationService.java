package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.response.ChoreCertifyReactionNotificationResponse;
import kr.hs.mirim.family.dto.response.ChoreCertifyRequestNotificationResponse;
import kr.hs.mirim.family.dto.response.QuestNotificationResponse;
import kr.hs.mirim.family.entity.chore.Chore;
import kr.hs.mirim.family.entity.chore.repository.ChoreRepository;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.entity.quest.Quest;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

import static kr.hs.mirim.family.controller.NotificationController.sseEmitters;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final ChoreRepository choreRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    /*
     * 심부름 생성 시 그룹원에게 알림을 보내는 기능
     * 심부름 생성 시 그룹원에게 요청자와 요청된 심부름의 제목을 알림으로 전송한다.
     *
     * - 매개변수 userIds : 그룹에 속한 특정 회원들에게만 알림을 보내기 위해 필요한 user id
     * - 매개변수 userIds = null : userIds가 null일 경우 나를 제외한 그룹원 모두에게 알림을 보낸다.
     *
     * 404 not found
     * - userIds가 null이 아니고 그룹에 속한 회원이 아닌 id가 존재할 경우
     *
     * 200 sse emitter
     * event:new quest
     * data:{"userNickname":"jamjam","questTitle":"quest title"}
     *
     * @author : m04j00
     * */
    @Transactional(readOnly = true)
    public void questNotification(Quest quest, List<Long> userIds) {
        Long groupId = quest.getGroup().getGroupId();
        Group group = groupRepository.findById(groupId).orElseThrow();
        if (userIds == null) {
            userIds = userRepository.findAllByGroup(group);
            userIds.remove(quest.getUser().getUserId());
        } else {
            List<User> usersInGroup = userRepository.findByGroupAndUserIdIn(group, userIds);
            if (usersInGroup.size() != userIds.size()) {
                throw new DataNotFoundException("알림을 보낼 그룹원 중 그룹에 속하지 않은 회원이 포함되어 있습니다.");
            }
        }
        QuestNotificationResponse data = new QuestNotificationResponse(quest.getUser().getUserNickname(), quest.getQuestTitle());

        for (Long id : userIds) {
            sseEmitterNotification(id, data, "new quest");
        }
    }

    /*
     * 요청된 심부름 수락 시 요청자에게 알림을 보내는 기능
     * 심부름 수락 시 요청자에게 수락자와 수락된 심부름의 제목을 알림으로 전송한다.
     *
     * 200 sse emitter
     * event:quest accept
     * data:{"userNickname":"sky","questTitle":"quest title"}
     *
     * @author : m04j00
     * */
    @Transactional(readOnly = true)
    public void questAcceptNotification(Quest quest, long acceptorId) {
        long requesterId = quest.getUser().getUserId();
        String acceptor = userRepository.getById(acceptorId).getUserNickname();
        String title = quest.getQuestTitle();
        QuestNotificationResponse data = new QuestNotificationResponse(acceptor, title);
        sseEmitterNotification(requesterId, data, "quest accept");
    }

    /*
     * 집안일 완료후 인증을 요청하는 알림을 보내는 기능
     * 집안일 담당자가 집안일을 완료한 후, 같은 그룹원(자신제외)에게 인증 요청 알림을 보내는 기능
     *
     * 200 그룹원들에게 알림 전송 성공(sse emitter)
     *
     * event : chore certify request
     * data : {"choreTitle" : "cooking dinner", "choreCategory" : "COOK", "userNickname" : "SRin23"}
     *
     * @author : SRin23
     */
    @Transactional(readOnly = true)
    public void choreCertifyNotification(Long choreId) {
        Chore chore = choreRepository.getById(choreId);
        List<Long> userIds = userRepository.findAllByGroup(chore.getGroup());
        userIds.remove(chore.getUser().getUserId());

        ChoreCertifyRequestNotificationResponse data = new ChoreCertifyRequestNotificationResponse(chore.getChoreTitle(), chore.getChoreCategory(), chore.getUser().getUserNickname());
        String notificationName = "chore certify request";

        for(Long userId : userIds){
            sseEmitterNotification(userId, data, notificationName);
        }

    }

    /*
     * 집안일 인증에 대한 응답(수락)을 요청하는 알림을 보내는 기능
     * 해당 집안일이 인증되었응을 알리는 응답을 집안일 담당자에게 보내는 기능
     *
     * 200 집안일 담당자에게 알림 전송 성공(sse emitter)
     *
     * event : chore certify reaction
     * data : {"choreTitle" : "cooking dinner", "choreCategory" : "COOK"}
     *
     * @author : SRin23
     */
    @Transactional(readOnly = true)
    public void choreCertifyReactionNotification(Long choreId) {
        Chore chore = choreRepository.getById(choreId);
        Long userId = chore.getUser().getUserId();

        ChoreCertifyReactionNotificationResponse data = new ChoreCertifyReactionNotificationResponse(chore.getChoreTitle(), chore.getChoreCategory());
        String notificationName = "chore certify reaction";
        sseEmitterNotification(userId, data, notificationName);
    }

    private void sseEmitterNotification(long userId, Object data, String notificationName) {
        if (sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitter = sseEmitters.get(userId);
            try {
                sseEmitter.send(SseEmitter.event().name(notificationName).data(data));
            } catch (Exception e) {
                sseEmitters.remove(userId);
            }
        }
    }

    private void sseEmitterNotification(long userId, Object data, String notificationName) {
        if (sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitter = sseEmitters.get(userId);

            try {
                sseEmitter.send(SseEmitter.event().name(notificationName).data(data));
            } catch (Exception e) {
                sseEmitters.remove(userId);
            }
        }
    }
}