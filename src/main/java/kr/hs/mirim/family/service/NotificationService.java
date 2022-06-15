package kr.hs.mirim.family.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hs.mirim.family.dto.response.ChoreNotificationResponse;
import kr.hs.mirim.family.dto.response.QuestNotificationResponse;
import kr.hs.mirim.family.entity.chore.Chore;
import kr.hs.mirim.family.entity.chore.repository.ChoreRepository;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.entity.quest.Quest;
import kr.hs.mirim.family.entity.quest.repository.QuestRepository;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Optional;

import static kr.hs.mirim.family.controller.NotificationController.sseEmitters;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final ChoreRepository choreRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Transactional(readOnly = true)
    public void questNotification(Quest quest) {
        Long groupId = quest.getGroup().getGroupId();
        Optional<Group> group = groupRepository.findById(groupId);
        List<Long> userIds = userRepository.findAllByGroup(group);
        Long getU = quest.getUser().getUserId();
        for (Long u : userIds) {
            System.out.println(u);
        }
        System.out.println("---------------");
        userIds.remove(getU);
        for (Long u : userIds) {
            System.out.println(u);
        }

        for (Long id : userIds) {
            if (sseEmitters.containsKey(id)) {

                SseEmitter sseEmitter = sseEmitters.get(id);
                try {
                    QuestNotificationResponse data = new QuestNotificationResponse(quest.getUser().getUserNickname(), quest.getQuestTitle());
                    sseEmitter.send(SseEmitter.event().name("new quest").data(data));
                } catch (Exception e) {
                    sseEmitters.remove(groupId);
                }
            }

        }

    }

    @Transactional(readOnly = true)
    public void choreCertifyNotification(Long choreId) {
        Chore chore = choreRepository.getById(choreId);
        Long groupId = chore.getGroup().getGroupId();

        if (sseEmitters.containsKey(groupId)) {
            SseEmitter sseEmitter = sseEmitters.get(groupId);
            try {
                ChoreNotificationResponse data = new ChoreNotificationResponse(chore.getChoreTitle(), chore.getChoreCategory(), chore.getChoreCheck(), chore.getUser().getUserNickname(), chore.getChoreDate(), chore.getCreatedDate().toLocalDate(), chore.getModifiedDate().toLocalDate());
                sseEmitter.send(SseEmitter.event().name("chore certify request").data(data));
            } catch (Exception e) {
                sseEmitters.remove(groupId);
            }
        }
    }

    @Transactional(readOnly = true)
    public void choreCertifyReactionNotification(Long choreId) {
        Chore chore = choreRepository.getById(choreId);
        Long groupId = chore.getGroup().getGroupId();

        if (sseEmitters.containsKey(groupId)) {
            SseEmitter sseEmitter = sseEmitters.get(groupId);
            try {
                ObjectMapper mapper = new ObjectMapper();
                ChoreNotificationResponse data = new ChoreNotificationResponse(chore.getChoreTitle(), chore.getChoreCategory(), chore.getChoreCheck(), chore.getUser().getUserNickname(), chore.getChoreDate(), chore.getCreatedDate().toLocalDate(), chore.getModifiedDate().toLocalDate());
                sseEmitter.send(SseEmitter.event().name("chore certify reaction").data(data));
            } catch (Exception e) {
                sseEmitters.remove(groupId);
            }
        }
    }

}