package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.*;
import kr.hs.mirim.family.dto.response.*;
import kr.hs.mirim.family.entity.chore.ChoreCategory;
import kr.hs.mirim.family.entity.chore.repository.ChoreRepository;
import kr.hs.mirim.family.entity.quest.repository.QuestRepository;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.exception.ConflictException;
import kr.hs.mirim.family.exception.DataNotFoundException;
import kr.hs.mirim.family.exception.BadRequestException;
import kr.hs.mirim.family.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ChoreRepository choreRepository;
    private final QuestRepository questRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse createUser (CreateUserRequest createUserRequest, BindingResult bindingResult){
        //형식이 맞지 않을때(null, 공백, 이메일 형식)
        formValidateException(bindingResult);

        //이미 가입된 이메일인지 확인
        if(userRepository.existsByUserEmail(createUserRequest.getUserEmail())){
            throw new ConflictException("이미 가입된 회원입니다.");
        }

        String encodePassword = createPassword(createUserRequest.getUserPassword());
        User user = new User(createUserRequest.getUserName(), createUserRequest.getUserNickname(), encodePassword, createUserRequest.getUserEmail(), "");

        userRepository.save(user);

        return CreateUserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userNickname(user.getUserNickname())
                .userEmail(user.getUserEmail())
                .build();
    }

    @Transactional
    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest, BindingResult bindingResult){
        //형식이 맞지 않을때(null, 공백, 이메일 형식)
        formValidateException(bindingResult);

        //존재하는 이메일인지 확인
        User user = userRepository.findByUserEmail(loginUserRequest.getUserEmail()).orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));
        groupCheck(user);

        //이메일과 비밀번호가 맞지 않을때
        if(!passwordEncoder.matches(loginUserRequest.getUserPassword(), user.getUserPassword())){
            throw new ForbiddenException("회원 정보가 일치하지 않습니다.");
        }

        return LoginUserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userNickname(user.getUserNickname())
                .userEmail(user.getUserEmail())
                .userImagePath(user.getUserImagePath())
                .groupId(user.getGroup().getGroupId())
                .build();
    }

    @Transactional
    public void deleteUser(long userId, DeleteUserRequest deleteUserRequest){
        User user = getUser(userId);
        passwordCheck(user, deleteUserRequest.getUserPassword());

        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUserPassword(long userId, UpdateUserPasswordRequest updateUserPasswordRequest, BindingResult bindingResult){
        User user = getUser(userId);
        formValidateException(bindingResult);
        passwordCheck(user, updateUserPasswordRequest.getUserPassword());
        if(updateUserPasswordRequest.getUserNewPassword().equals(updateUserPasswordRequest.getUserNewPasswordCheck())){
            user.updateUserPassword(createPassword(updateUserPasswordRequest.getUserNewPassword()));
        }else{
            throw new ConflictException("확인 비밀번호가 새비밀번호와 일치하지 않습니다.");
        }
    }

    /*
     * checkUserPassword 실행 후 회원 수정 진행
     * 수정이 정상적으로 처리된 경우 200
     * 존재하지 않는 ID일 경우 404
     * 잘못된 형식의 값이 들어오는 경우 409
     *
     * @author: SRin23
     */
    @Transactional
    public void updateUser(long userId, UpdateUserRequest updateUserRequest, BindingResult bindingResult){
        User user = getUser(userId);
        formValidateException(bindingResult);
        user.updateUser(updateUserRequest.getUserName(), updateUserRequest.getUserNickname());
    }

    @Transactional
    public UserFindResponse findUserInfo(long userId, String date) {
        User user = getUser(userId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth localDate;
        try{
            localDate = YearMonth.parse(date, formatter);
        }catch(Exception e){
            throw new BadRequestException("잘못된 형식입니다.");
        }

        QuestKingResponse questKing = questRepository.questKingMonth(user.getGroup().getGroupId(), localDate);
        if(questKing!=null){
            if(questKing.getUserId()!=userId){
                questKing = null;
            }
        }

        List<ChoreKingResponse> choreList = choreRepository.monthKing(user.getGroup().getGroupId(), localDate);
        HashMap<ChoreCategory, ChoreKingResponse> choreMap = new HashMap<>();
        for (ChoreKingResponse choreKingResponse : choreList) {
            if (choreMap.containsKey(choreKingResponse.getCategory())) {
                if (choreMap.get(choreKingResponse.getCategory()).getCount() < choreKingResponse.getCount())
                    choreMap.put(choreKingResponse.getCategory(), choreKingResponse);
            } else {
                choreMap.put(choreKingResponse.getCategory(), choreKingResponse);
            }
            if(choreMap.size() == 3) break;
        }

        List<ChoreKingResponse> choreKingResult = new ArrayList();

        if(!choreMap.isEmpty()){
            for(ChoreKingResponse item : choreMap.values()){
                if(item.getUserId()==userId){
                    choreKingResult.add(item);
                }
            }
        }

        return UserFindResponse.builder()
                .userName(user.getUserName())
                .userNickname(user.getUserNickname())
                .userEmail(user.getUserEmail())
                .userImagePath(user.getUserImagePath())
                .king(
                        UserFindKingResponse.builder()
                                .choreKing(choreKingResult)
                                .questKing(questKing)
                                .build()
                )
                .build();
    }

    private String createPassword(String userPassword){
        return passwordEncoder.encode(userPassword);
    }

    private User getUser(long userId){
        return userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));
    }

    private void passwordCheck(User user, String userPassword){
        if(!passwordEncoder.matches(userPassword, user.getUserPassword())){
            throw new ForbiddenException("회원 정보가 일치하지 않습니다.");
        }
    }

    private void groupCheck(User user){
        //회원이 그룹에 가입되어 있는지 확인
        if(user.getGroup()==null){
            throw new DataNotFoundException("그룹에 가입되어 있지 않은 회원입니다.");
        }
    }

    private void formValidateException(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException("유효하지 않은 형식입니다.");
        }
    }
}
