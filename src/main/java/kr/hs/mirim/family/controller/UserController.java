package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.UserSignUpDto;
import kr.hs.mirim.family.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor //이게 어떻게 동작하기에 초기화되지 않은 UserService 객체를 에러없이 사용할 수 있게 해주는거지?
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public void signUp(@RequestBody UserSignUpDto userSignUpDto){
        userService.signUp(userSignUpDto);
    }
}
