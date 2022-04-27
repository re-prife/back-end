package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.UserSignUpDto;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signUp(UserSignUpDto userSignUpDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IllegalStateException("유효하지 않은 형식입니다.");
        }

        if(userRepository.existsByUserEmail(userSignUpDto.getUserEmail())){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        String encodePassword = passwordEncoder.encode(userSignUpDto.getUserPassword());
        User user = User.builder()
                .userName(userSignUpDto.getUserName())
                .userEmail(userSignUpDto.getUserEmail())
                .userPassword(encodePassword)
                .build();

        userRepository.save(user);
    }
}
