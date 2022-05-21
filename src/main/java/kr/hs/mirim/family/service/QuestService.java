package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.CreateQuestRequest;
import kr.hs.mirim.family.dto.response.QuestListResponse;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.entity.quest.Quest;
import kr.hs.mirim.family.entity.quest.repository.QuestRepository;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.exception.BadRequestException;
import kr.hs.mirim.family.exception.ConflictException;
import kr.hs.mirim.family.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

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
        User user = getUser(userId);
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

    /* *
     * 심부름 조회 기능
     * 그룹이 존재하지 않으면 404 Not found
     *
     * @author : m04j00
     * */
    public List<QuestListResponse> questList(long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        });
        List<Quest> questList = questRepository.findAllByGroup(group);
        return questList.stream()
                .map(QuestListResponse::of)
                .collect(Collectors.toList());
    }

    /*
     * 심부름을 수락하거나 수락한 후 취소하는 메소드
     * - quest의 acceptUserId를 변경하는 기능
     * - 컬럼명 acceptUserId, 자바 변수로 acceptorId로 사용
     *
     * 404 not found
     * - groupId가 존재하지 않을 시
     * - questId가 존재하지 않거나 group에 속하지 않을 경우
     * - acceptorId가 존재하지 않거나 group에 속하지 않을 경우
     * 409 conflict
     * - completeCheck가 true일 경우 (이미 해결된 심부름일 경우)
     * - quest의 acceptUserId가 -1이 아니고 API의 매개변수 acceptorId와 일치하지 않을 경우 (수락자가 존재하는데, 다른 사람이 API를 요청한 경우)
     * - quest 추가한 사람이 API를 요청할 때
     *
     * @author: m04j00
     * */
    public void questAcceptor(long groupId, long questId, long acceptorId) {
        relationValidate(groupId, questId, acceptorId);
        Quest quest = getQuest(questId);

        if (quest.isCompleteCheck()) {
            throw new ConflictException("완료된 심부름입니다.");
        }
        if (quest.getAcceptUserId() != -1) {
            if (quest.getAcceptUserId() != acceptorId) {
                throw new ConflictException("심부름 수락자가 아닌 사람은 수락을 취소할 수 없습니다.");
            }
            acceptorId = -1;
        } else if (quest.getUser().getUserId() == acceptorId) {
            throw new ConflictException("심부름을 추가한 사람은 수락할 수 없습니다.");
        }

        questRepository.updateAcceptUserId(questId, acceptorId);
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

    private User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 회원입니다.");
        });
    }

    private Quest getQuest(long id) {
        return questRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFoundException("심부름이 존재하지 않습니다.");
        });
    }

    private void relationValidate(long groupId, long questId, long userId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        });
        if (!questRepository.existsByQuestIdAndGroup(questId, group)) {
            throw new DataNotFoundException("심부름이 존재하지 않습니다.");
        }
        if (!userRepository.existsByUserIdAndGroup(userId, group)) {
            throw new DataNotFoundException("존재하지 않는 회원입니다.");
        }
    }