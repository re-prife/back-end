package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.CreateUserRequest;
import kr.hs.mirim.family.dto.request.LoginUserRequest;
import kr.hs.mirim.family.dto.response.LoginUserResponse;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.exception.AlreadyExistsException;
import kr.hs.mirim.family.exception.DataNotFoundException;
import kr.hs.mirim.family.exception.BadRequestException;
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
            throw new AlreadyExistsException("이미 가입된 회원입니다.");
        }

        String encodePassword = passwordEncoder.encode(createUserRequest.getUserPassword());
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
            throw new BadRequestException("회원 정보가 일치하지 않습니다.");
        }

        return new LoginUserResponse(user);
    }

    public void formValidateException(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException("유효하지 않은 형식입니다.");
        }
    }
}
