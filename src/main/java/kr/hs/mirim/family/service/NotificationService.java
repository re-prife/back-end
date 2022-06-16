package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.response.ChoreCertifyReactionNotificationResponse;
import kr.hs.mirim.family.dto.response.ChoreCertifyRequestNotificationResponse;
import kr.hs.mirim.family.dto.response.QuestNotificationResponse;
import kr.hs.mirim.family.entity.chore.Chore;
import kr.hs.mirim.family.entity.chore.repository.ChoreRepository;
import kr.hs.mirim.family.entity.quest.Quest;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
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

    @Transactional(readOnly = true)
    public void questNotification(Quest quest) {
        Long groupId = quest.getGroup().getGroupId();

        if (sseEmitters.containsKey(groupId)) {
            SseEmitter sseEmitter = sseEmitters.get(groupId);
            try {
                QuestNotificationResponse data = new QuestNotificationResponse(quest.getUser().getUserNickname(), quest.getQuestTitle());
                sseEmitter.send(SseEmitter.event().name("new quest").data(data));
            } catch (Exception e) {
                sseEmitters.remove(groupId);
            }
        }
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

}