package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.UserSignUpDto;
import kr.hs.mirim.family.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void signUp(@RequestBody @Valid UserSignUpDto userSignUpDto, BindingResult bindingResult){
        userService.signUp(userSignUpDto, bindingResult);
    }
}
