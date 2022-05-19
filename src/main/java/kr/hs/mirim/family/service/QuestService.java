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

    /* *
     * 심부름 추가 기능
     * 심부름 추가 완료 시 200 OK
     *
     * dto form이 일치하지 않으면 400 Bad request
     * 그룹이 존재하지 않으면 404 Not found
     * 계정이 존재하지 않으면 404 Not found
     *
     * Quest Entity의 기본값 세팅
     * - acceptUserId : 심부름 수락자의 id로, 심부름 추가 시 수락자가 없으므로 기본값 -1
     * - completeCheck : 심부름 수락자가 심부름을 완료했을 경우 true, 아닐 경우 기본값 false
     *
     * @author: m04j00
     * */
    public void createQuest(long groupId, long userId, CreateQuestRequest request, BindingResult bindingResult) {
        formValidate(bindingResult);
        existsGroup(groupId);
        User user = existsUser(userId);
        Quest quest = Quest.builder()
                .questTitle(request.getQuestTitle())
                .questContent(request.getQuestContent())
                .acceptUserId((long) -1)
                .completeCheck(false)
                .user(user)
                .group(user.getGroup())
                .build();
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
