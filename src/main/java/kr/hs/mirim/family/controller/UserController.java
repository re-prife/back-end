package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.*;
import kr.hs.mirim.family.dto.response.LoginUserResponse;
import kr.hs.mirim.family.dto.response.UserFindResponse;
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
    public void createUser(@RequestBody @Valid CreateUserRequest createUserRequest, BindingResult bindingResult){
        userService.createUser(createUserRequest, bindingResult);
    }

    @PostMapping("/login")
    public LoginUserResponse loginUser(@RequestBody @Valid LoginUserRequest loginUserRequest, BindingResult bindingResult){
        return userService.loginUser(loginUserRequest, bindingResult);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId, @RequestBody DeleteUserRequest deleteUserRequest){
        userService.deleteUser(userId, deleteUserRequest);
    }

    @PutMapping("/{userId}/password")
    public void updateUserPassword(@PathVariable long userId, @RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest, BindingResult bindingResult){
        userService.updateUserPassword(userId, updateUserPasswordRequest, bindingResult);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable long userId, @RequestBody @Valid UpdateUserRequest updateUserRequest, BindingResult bindingResult){
        userService.updateUser(userId, updateUserRequest, bindingResult);
    }

    @GetMapping("/{userId}")
    public UserFindResponse getKingOfTheMonth(@PathVariable long userId, @RequestParam String date){
        return userService.findKingOfTheMonthById(userId,date);
    }
}
