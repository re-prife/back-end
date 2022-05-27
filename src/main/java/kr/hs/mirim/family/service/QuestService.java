package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.QuestRequest;
import kr.hs.mirim.family.dto.response.QuestResponse;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.entity.quest.Quest;
import kr.hs.mirim.family.entity.quest.repository.QuestRepository;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.exception.BadRequestException;
import kr.hs.mirim.family.exception.ConflictException;
import kr.hs.mirim.family.exception.DataNotFoundException;
import kr.hs.mirim.family.exception.MethodNotAllowedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void createQuest(long groupId, long userId, QuestRequest request, BindingResult bindingResult) {
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
    @Transactional
    public List<QuestResponse> questList(long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        });
        List<Quest> questList = questRepository.findAllByGroup(group);
        return questList.stream()
                .map(QuestResponse::of)
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
    @Transactional
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

    /*
     * 수락자가 심부름 완료 시 요청자가 완료 요청할 때 사용하는 기능
     * - API 호출 시 completeCheck를 true로 변경
     * - 컬럼명 requestUserId, 자바 코드 상 requesterId로 사용
     * 404 not found
     * - groupId가 존재하지 않을 시
     * - questId가 존재하지 않거나 group에 속하지 않을 경우
     * - requesterId가 존재하지 않거나 group에 속하지 않을 경우
     * 409 conflict
     * - quest의 requestUserId가 API의 매개변수 requesterId와 일치하지 않을 경우 (요청자만이 완료 여부를 판단할 수 있음)
     * - quest의 acceptUserId가 -1인 경우 (심부름을 수락한 사람이 있어야지만 완료 여부를 판단할 수 있음)
     *
     * @author : m04j00
     * */
    @Transactional
    public void questCompleteCheck(long groupId, long questId, long requesterId) {
        relationValidate(groupId, questId, requesterId);
        Quest quest = getQuest(questId);

        if (quest.getUser().getUserId() != requesterId) {
            throw new ConflictException("심부름 요청자가 아닙니다.");
        }
        if (quest.getAcceptUserId() == -1) {
            throw new ConflictException("심부름을 수락한 사람이 없습니다.");
        }

        questRepository.updateCompleteCheck(questId);
    }

    /*
     * 심부름 삭제 기능 구현
     * - 심부름을 요청한 사람만이 심부름 요청 전에 삭제할 수 있다.
     *
     * 404 not found
     * - groupId가 존재하지 않을 경우
     * - quest가 group에 속해있지 않을 경우
     * - user가 group에 속해있지 않을 경우
     *
     * 409 conflict
     * - quest를 생성한 user가 아닌 경우
     *
     * 405 methodNotAllowed
     * - 완료된 심부름을 삭제하려는 경우
     *
     * @author : m04j00
     * */
    public void deleteQuest(long groupId, long questId, long userId) {
        relationValidate(groupId, questId, userId);
        Quest quest = getQuest(questId);

        if (quest.getUser().getUserId() != userId) {
            throw new ConflictException("심부름을 요청한 사람만 삭제할 수 있습니다.");
        }
        if (quest.isCompleteCheck()) {
            throw new MethodNotAllowedException("완료된 심부름은 삭제할 수 없습니다.");
        }

        questRepository.deleteById(questId);
    }

    /*
     * 심부름 수정 기능
     * - 심부름 요청자만이 수락자가 없을 경우에만 수정 가능
     *
     * 404 not found
     * - groupId가 존재하지 않을 시
     * - questId가 존재하지 않거나 group에 속하지 않을 경우
     * - requesterId가 존재하지 않거나 group에 속하지 않을 경우
     *
     * 409 conflict
     * - requesterId가 quest의 심부름 요청자와 일치하지 않을 경우
     *
     * 405 method not allowed
     * - quest의 수락자가 존재할 경우
     *
     * @author : m04j00
     * */
    @Transactional
    public QuestResponse updateQuest(long groupId, long questId, QuestRequest request, long requesterId) {
        relationValidate(groupId, questId, requesterId);
        Quest quest = getQuest(questId);

        if (quest.getUser().getUserId() != requesterId) {
            throw new ConflictException("심부름 요청자가 아닙니다.");
        }
        if (quest.getAcceptUserId() != -1) {
            throw new MethodNotAllowedException("심부름 수락자가 있으면 수정이 불가능합니다.");
        }

        quest.updateQuest(request.getQuestTitle(), request.getQuestContent());

        return QuestResponse.builder()
                .questId(quest.getQuestId())
                .requestUserId(quest.getUser().getUserId())
                .questTitle(quest.getQuestTitle())
                .completeCheck(quest.isCompleteCheck())
                .acceptUserId(quest.getAcceptUserId())
                .build();
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
}