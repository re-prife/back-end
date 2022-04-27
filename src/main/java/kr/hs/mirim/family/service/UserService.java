package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.UserSignUpDto;
import kr.hs.mirim.family.entity.User.User;
import kr.hs.mirim.family.entity.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void signUp(UserSignUpDto userSignUpDto){
        String encodePassword = passwordEncoder.encode(userSignUpDto.getUserPassword());
        User user = User.builder()
                .userName(userSignUpDto.getUserName())
                .userEmail(userSignUpDto.getUserEmail())
                .userPassword(encodePassword)
                .build();

        userRepository.save(user);
    }
}
