package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.*;
import kr.hs.mirim.family.dto.response.LoginUserResponse;
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

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser (CreateUserRequest createUserRequest, BindingResult bindingResult){
        //형식이 맞지 않을때(null, 공백, 이메일 형식)
        formValidateException(bindingResult);

        //이미 가입된 이메일인지 확인
        if(userRepository.existsByUserEmail(createUserRequest.getUserEmail())){
            throw new ConflictException("이미 가입된 회원입니다.");
        }

        String encodePassword = createPassword(createUserRequest.getUserPassword());
        User user = new User(createUserRequest.getUserName(), createUserRequest.getUserNickname(), encodePassword, createUserRequest.getUserEmail(), "");

        userRepository.save(user);
    }

    @Transactional
    public LoginUserResponse loginUser(LoginUserRequest loginUserRequest, BindingResult bindingResult){
        //형식이 맞지 않을때(null, 공백, 이메일 형식)
        formValidateException(bindingResult);

        //존재하는 이메일인지 확인
        User user = userRepository.findByUserEmail(loginUserRequest.getUserEmail()).orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));

        //이메일과 비밀번호가 맞지 않을때
        if(!passwordEncoder.matches(loginUserRequest.getUserPassword(), user.getUserPassword())){
            throw new ForbiddenException("회원 정보가 일치하지 않습니다.");
        }

        return new LoginUserResponse(user);
    }

    @Transactional
    public void deleteUser(long userId, DeleteUserRequest deleteUserRequest){
        User user = getUser(userId);
        passwordCheck(user, deleteUserRequest.getUserPassword());

        userRepository.deleteById(userId);
    }

    @Transactional
    public void checkUserPassword(long userId, CheckUserPasswordRequest checkUserPasswordRequest){
        User user = getUser(userId);
        passwordCheck(user, checkUserPasswordRequest.getUserPassword());
    }

    /*
     * checkUserPassword 실행 후 회원 수정 진행
     * 수정이 정상적으로 처리된 경우 200
     * 존재하지 않는 ID일 경우 404
     * 잘못된 형식의 값이 들어오는 경우 409
     * 
     * 비밀번호 암호화 후, update진행
     * @author: SRin23
     */
    @Transactional
    public void updateUser(long userId, UpdateUserRequest updateUserRequest, BindingResult bindingResult){
        User user = getUser(userId);
        formValidateException(bindingResult);
        String encodePassword = createPassword(updateUserRequest.getUserPassword());
        user.updateUser(updateUserRequest.getUserName(), updateUserRequest.getUserNickname(), encodePassword, updateUserRequest.getUserImageName());
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

    private void formValidateException(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException("유효하지 않은 형식입니다.");
        }
    }
}
