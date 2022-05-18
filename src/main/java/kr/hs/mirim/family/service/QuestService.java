package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.CreateQuestRequest;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.entity.quest.Quest;
import kr.hs.mirim.family.entity.quest.repository.QuestRepository;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.exception.BadRequestException;
import kr.hs.mirim.family.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository questRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public void createQuest(long groupId, long userId, CreateQuestRequest request, BindingResult bindingResult) {
        formValidate(bindingResult);
        existsGroup(groupId);
        User user = existsUser(userId);
        Quest quest = new Quest(request.getQuestTitle(), request.getQuestContent(), (long) -1, false, user, user.getGroup());
        questRepository.save(quest);
    }

    private void formValidate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("유효하지 않은 형식입니다.");
        }
    }

    private void existsGroup(long groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        }
    }

    private User existsUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 회원입니다.");
        });
    }

}
