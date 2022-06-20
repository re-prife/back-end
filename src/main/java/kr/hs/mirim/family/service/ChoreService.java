package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.CreateChoreRequest;
import kr.hs.mirim.family.dto.response.ChoreListResponse;
import kr.hs.mirim.family.entity.chore.Chore;
import kr.hs.mirim.family.entity.chore.ChoreCategory;
import kr.hs.mirim.family.entity.chore.ChoreCheck;
import kr.hs.mirim.family.entity.chore.repository.ChoreRepository;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.YearMonth;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static kr.hs.mirim.family.entity.chore.ChoreCheck.*;


@Service
@RequiredArgsConstructor
public class ChoreService {
    private final ChoreRepository choreRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    /*
     * 집안일 당번 추가하기
     * 성공시, 해당 그룹에 집안일 당번 등록 후 200
     * group ID 또는 담당자 ID가 존재하지 않으면 404 Not Found
     * 값이 다 들어오지 않으면 409 Conflict
     *
     * 당번 추가시, 초기 choreCheck는 인증 요청인 'REQUEST'로 표기
     * choreCategory에 해당하는 enum에 값 있는지 확인 후, 없으면 404 error 반환
     * 해당 그룹 내 유저가 아닐 경우 404 반환
     *
     * @author : SRin23
     */
    @Transactional
    public void createChore(long groupId, CreateChoreRequest createChoreRequest, BindingResult bindingResult) {
        Group group = getGroup(groupId);
        User user = getUser(createChoreRequest.getChoreUserId());
        userInGroup(user.getGroup().getGroupId(), group.getGroupId());

        if (createChoreRequest.getChoreDate().isBefore(LocalDate.now())) {
            throw new DateOverException("이미 지난 날짜는 당번을 생성할 수 없습니다.");
        }

        ChoreCategory choreCategory = enumCategoryValid(createChoreRequest.getChoreCategory());

        formValidate(bindingResult);

        Chore chore = Chore.builder()
                .choreTitle(createChoreRequest.getChoreTitle())
                .choreCheck(BEFORE)
                .choreCategory(choreCategory)
                .choreDate(createChoreRequest.getChoreDate())
                .user(user)
                .group(group)
                .build();

        choreRepository.save(chore);
    }

    @Transactional
    public List<ChoreListResponse> choreListOneDay(long groupId, String date) {
        existsGroup(groupId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new BadRequestException("잘못된 형식입니다.");
        }
        return choreRepository.findByChoreGroup_GroupIdAndDate(groupId, localDate);
    }

    @Transactional
    public List<ChoreListResponse> choreListMonth(long groupId, String date) {
        existsGroup(groupId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth localDate;
        try {
            localDate = YearMonth.parse(date, formatter);
        } catch (Exception e) {
            throw new BadRequestException("잘못된 형식입니다.");
        }

        return choreRepository.findByChoreGroup_GroupIdAndDateMonth(groupId, localDate);
    }

    @Transactional(noRollbackFor = DateOverException.class)
    public void choreCertify(long groupId, long choreId) {
        existsGroup(groupId);
        choreCheckFail(getChore(choreId));
        Chore chore = choreRepository.getById(choreId);
        existsChoreInGroup(groupId, chore.getGroup().getGroupId());
        if (chore.getChoreCheck().equals(BEFORE)) {
            choreRepository.updateChoreCheck(choreId, REQUEST);
        } else if (chore.getChoreCheck().equals(FAIL)) {
            throw new DateOverException("이미 당번 활동에 대해 인증받지 못하고 종료된 집안일 입니다.");
        } else {
            throw new ConflictException("이미 인증 요청이 끝난 집안일입니다.");
        }
    }

    /*
     * 인증요청에 대한 응답 처리
     * 성공시, 해당 집안일에 ChoreCheck를 reaction으로 등록 후 200
     *
     * 존재하지 않는 그룹, 집안일일 경우 404
     * 해당 그룹의 집안일이 아닐 경우 404
     *
     * 형식이 맞지 않는 경우 409
     * 이미 SUCCESS인데, REQUEST를 reaction으로 받는 경우 409
     * REQUEST 상태가 아닌데 reaction 응답을 받는 경우 409
     * reaction이 FAIL일시, 즉시 ChoreCheck를 REQUEST(인증요청)로 변환
     * 이전의 ChoreCheck와 reaction이 같은 경우 변화 없음
     *
     * @author : SRin23
     */
    @Transactional(noRollbackFor = DateOverException.class)
    public void choreCertifyReaction(long groupId, long choreId) {
        existsGroup(groupId);
        choreCheckFail(getChore(choreId));
        Chore chore = choreRepository.getById(choreId);
        existsChoreInGroup(groupId, chore.getGroup().getGroupId());

        if (chore.getChoreCheck().equals(BEFORE)) {
            throw new BadRequestException("인증 요청 되지 않은 집안일 입니다.");
        } else if (chore.getChoreCheck().equals(FAIL)) {
            throw new DateOverException("이미 당번 활동에 대해 인증받지 못하고 종료된 집안일 입니다.");
        } else if (chore.getChoreCheck().equals(SUCCESS)) {
            throw new DateOverException("이미 당번 활동이 성공하여 종료된 집안일 입니다.");
        } else {
            choreRepository.updateChoreCheck(choreId, SUCCESS);
        }
    }


    /*
     * 집안일 삭제 기능
     * - 인증이 완료(SUCCESS, FAIL)되지 않은 집안일을 삭제하는 기능
     *
     * 404 not found
     * - groupId가 존재하지 않을 경우
     * - choreId가 존재하지 않을 경우
     *
     * 409 conflict
     * - 집안일이 해당 그룹에 속하지 않을 경우
     *
     * 405 method not allowed
     * - 인증이 완료된 집안일을 삭제하려는 경우
     *
     * @author : m04j00
     * */
    @Transactional
    public void deleteChore(long groupId, long choreId) {
        existsGroup(groupId);
        Chore chore = getChore(choreId);
        existsChoreInGroup(groupId, chore.getGroup().getGroupId());

        if (chore.getChoreCheck().equals(SUCCESS) || chore.getChoreCheck().equals(FAIL)) {
            throw new MethodNotAllowedException("인증이 완료된 집안일은 삭제가 불가능합니다.");
        }

        choreRepository.deleteById(choreId);
    }

    protected void choreCheckFail(Chore chore) {
        if (chore.getChoreDate().isBefore(LocalDate.now())) {
            if (chore.getChoreCheck().equals(REQUEST) || chore.getChoreCheck().equals(BEFORE)) {
                choreRepository.updateChoreCheck(chore.getChoreId(), FAIL);
            }
        }
    }

    /* 예외 처리 */
    private User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 회원입니다.");
        });
    }

    private void existsGroup(long groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        }
    }


    private Group getGroup(long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        });
    }

    private void existsChoreInGroup(long groupId, long choreGroupId) {
        if (choreGroupId != groupId) {
            throw new DataNotFoundException("해당 그룹내에 존재하지 않는 집안일 입니다.");
        }
    }

    private Chore getChore(long choreId) {
        return choreRepository.findById(choreId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 집안일입니다.");
        });
    }

    private void userInGroup(long userGroupId, long groupId) {
        if (userGroupId != groupId) {
            throw new DataNotFoundException("그룹 내 존재하지 않는 회원입니다.");
        }
    }

    private void formValidate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("유효하지 않은 형식입니다.");
        }
    }

    public ChoreCategory enumCategoryValid(String category) {
        try {
            return ChoreCategory.valueOf(category);
        } catch (Exception e) {
            throw new DataNotFoundException("존재하지 않는 집안일 카테고리입니다.");
        }
    }

    public ChoreCheck enumCheckValid(String check, ChoreCheck choreCheck) {
        if (!check.isEmpty()) {
            try {
                return ChoreCheck.valueOf(check);
            } catch (Exception e) {
                throw new DataNotFoundException("존재하지 않는 형식의 값입니다.");
            }
        }
        return choreCheck;
    }
}
