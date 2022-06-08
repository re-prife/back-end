package kr.hs.mirim.family.service;

import kr.hs.mirim.family.entity.Notification.Notification;
import kr.hs.mirim.family.entity.Notification.repository.NotificationRepository;
import kr.hs.mirim.family.entity.quest.Quest;
import kr.hs.mirim.family.entity.Notification.repository.EmitterRepository;
import kr.hs.mirim.family.entity.quest.repository.QuestRepository;
import kr.hs.mirim.family.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

import static kr.hs.mirim.family.controller.NotificationController.sseEmitters;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final QuestRepository questRepository;
    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    public void questNotification(Quest quest) {
        Long groupId = quest.getGroup().getGroupId();

        if (sseEmitters.containsKey(groupId)) {
            SseEmitter sseEmitter = sseEmitters.get(groupId);
            try {
                String data = quest.getUser().getUserNickname() + "님이 심부름을 요청했습니다.\n" + quest.getQuestTitle();
                System.out.println(data);
                sseEmitter.send(SseEmitter.event().name("new quest").data(data));
            } catch (Exception e) {
                sseEmitters.remove(groupId);
            }
        }
    }

    public SseEmitter subscribe(Long userId) {
        String emitterId = makeTimeIncludeId(userId);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(60L * 1000L * 60L));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(userId);
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + userId + "]");

        return emitter;
    }

    public void send(User user, String title) {
        Notification notification = notificationRepository.save(new Notification(user, title, false));

        String userId = String.valueOf(user.getUserId());
        String eventId = userId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(userId);
        String data = user.getUserNickname() + "님이 심부름을 요청했습니다.\n" + title;
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, data);
                }
        );
    }

    private String makeTimeIncludeId(Long memberId) {
        return memberId + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }
}