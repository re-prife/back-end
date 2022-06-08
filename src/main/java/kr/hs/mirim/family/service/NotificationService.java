package kr.hs.mirim.family.service;

import kr.hs.mirim.family.entity.quest.Quest;
import kr.hs.mirim.family.entity.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static kr.hs.mirim.family.controller.NotificationController.sseEmitters;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final QuestRepository questRepository;

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
}