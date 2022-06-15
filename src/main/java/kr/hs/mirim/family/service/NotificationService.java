package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.response.ChoreNotificationResponse;
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

    @Transactional(readOnly = true)
    public void choreCertifyNotification(Long choreId) {
        Chore chore = choreRepository.getById(choreId);
        List<Long> userIds = userRepository.findByGroup(chore.getGroup().getGroupId());
        userIds.remove(chore.getUser().getUserId());

        ChoreNotificationResponse data = new ChoreNotificationResponse(chore.getChoreTitle(), chore.getChoreCategory(), chore.getChoreCheck(), chore.getUser().getUserNickname(), chore.getChoreDate(), chore.getCreatedDate().toLocalDate(), chore.getModifiedDate().toLocalDate());
        String notificationName = "chore certify request";

        for(Long userId : userIds){
            sseEmitterNotification(userId, data, notificationName);
        }

    }

    @Transactional(readOnly = true)
    public void choreCertifyReactionNotification(Long choreId) {
        Chore chore = choreRepository.getById(choreId);
        Long userId = chore.getUser().getUserId();

        ChoreNotificationResponse data = new ChoreNotificationResponse(chore.getChoreTitle(), chore.getChoreCategory(), chore.getChoreCheck(), chore.getUser().getUserNickname(), chore.getChoreDate(), chore.getCreatedDate().toLocalDate(), chore.getModifiedDate().toLocalDate());
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