package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.CreateChoreRequest;
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

import static kr.hs.mirim.family.entity.chore.ChoreCategory.fromString;
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
        formValidate(bindingResult);
        User user = getUser(createChoreRequest.getChoreUserId());
        Group group = getGroup(groupId);
        ChoreCategory choreCategory = fromString(createChoreRequest.getChoreCategory());
        Chore chore = Chore.builder()
                .choreTitle(createChoreRequest.getChoreTitle())
                .choreCheck(REQUEST)
                .choreCategory(choreCategory)
                .choreDate(createChoreRequest.getChoreDate())
                .user(user)
                .group(group)
                .build();

        choreRepository.save(chore);
    }

    private User getUser(long userId){
        return userRepository.findByUserId(userId).orElseThrow(()-> {
            throw new DataNotFoundException("존재하지 않는 회원입니다.");
        });
    }

    private Group getGroup(long groupId){
        return groupRepository.findByGroupId(groupId).orElseThrow(()->{
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        });
    }

    private void formValidate(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException("유효하지 않은 형식입니다.");
        }
    }

}
