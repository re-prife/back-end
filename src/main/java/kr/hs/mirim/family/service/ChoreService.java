package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.ChoreListOneDayRequest;
import kr.hs.mirim.family.dto.request.CreateChoreRequest;
import kr.hs.mirim.family.dto.response.ChoreListMonthResponse;
import kr.hs.mirim.family.dto.response.ChoreListOneDayResponse;
import kr.hs.mirim.family.entity.chore.Chore;
import kr.hs.mirim.family.entity.chore.ChoreCategory;
import kr.hs.mirim.family.entity.chore.repository.ChoreRepository;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.exception.BadRequestException;
import kr.hs.mirim.family.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static kr.hs.mirim.family.entity.chore.ChoreCheck.BEFORE;
import static kr.hs.mirim.family.entity.chore.ChoreCheck.REQUEST;

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
     *
     * @author : SRin23
     */
    @Transactional
    public void createChore(long groupId, CreateChoreRequest createChoreRequest, BindingResult bindingResult){
        Group group = getGroup(groupId);
        User user = getUser(createChoreRequest.getChoreUserId());
        formValidate(bindingResult);

        Chore chore = Chore.builder()
                .choreTitle(createChoreRequest.getChoreTitle())
                .choreCheck(BEFORE)
                .choreCategory(enumValid(createChoreRequest.getChoreCategory()))
                .choreDate(createChoreRequest.getChoreDate())
                .user(user)
                .group(group)
                .build();

        choreRepository.save(chore);
    }

    @Transactional
    public ChoreListOneDayResponse choreListOneDay(long groupId, ChoreListOneDayRequest choreListOneDayRequest){
        existsGroup(groupId);
        return ChoreListOneDayResponse.of(choreRepository.findByChoreGroupAndDate(groupId, choreListOneDayRequest.getDate()));
    }

    @Transactional
    public ChoreListMonthResponse choreListMonth(long groupId, String date){
        existsGroup(groupId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth localDate = YearMonth.parse(date, formatter);

        return ChoreListMonthResponse.of(choreRepository.findByChoreGroupAndDateMonth(groupId, localDate));
    }

    @Transactional
    public void choreCertify(long groupId, long choreId){
        existsGroup(groupId);
        Chore chore = getChore(choreId);
        if(chore.getGroup().getGroupId()!=groupId){
            throw new DataNotFoundException("해당 그룹내에 존재하지 않는 집안일 입니다.");
        }
        choreRepository.updateChoreCheck(choreId, REQUEST);
    }

    /* 예외 처리 */
    private User getUser(long userId){
        return userRepository.findById(userId).orElseThrow(()-> {
            throw new DataNotFoundException("존재하지 않는 회원입니다.");
        });
    }

    private void existsGroup(long groupId){
        if (!groupRepository.existsById(groupId)) {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        }
    }

    private Group getGroup(long groupId){
        return groupRepository.findById(groupId).orElseThrow(()->{
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        });
    }

    private Chore getChore(long choreId){
        return choreRepository.findById(choreId).orElseThrow(()->{
            throw new DataNotFoundException("존재하지 않는 집안일입니다.");
        });
    }

    private void formValidate(BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            throw new BadRequestException("유효하지 않은 형식입니다.");
        }
    }

    public ChoreCategory enumValid(String category){
        try {
            return ChoreCategory.valueOf(category);
        } catch (Exception e) {
            throw new DataNotFoundException("존재하지 않는 목록의 형식입니다.");
        }
    }
}
