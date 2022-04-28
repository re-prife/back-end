package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.CreateUserRequest;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.exception.AlreadyExistsException;
import kr.hs.mirim.family.exception.FormValidateException;
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
        if(bindingResult.hasErrors()){
            throw new FormValidateException("유효하지 않은 형식입니다.");
        }

        if(userRepository.existsByUserEmail(createUserRequest.getUserEmail())){
            throw new AlreadyExistsException("이미 가입된 회원입니다.");
        }

        String encodePassword = passwordEncoder.encode(createUserRequest.getUserPassword());
        User user = new User(createUserRequest.getUserName(), createUserRequest.getUserNickname(), encodePassword, createUserRequest.getUserEmail());

        userRepository.save(user);
    }
}
