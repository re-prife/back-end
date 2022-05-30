package kr.hs.mirim.family.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hs.mirim.family.dto.request.*;
import kr.hs.mirim.family.dto.response.LoginUserResponse;
import kr.hs.mirim.family.dto.response.UserFindResponse;
import kr.hs.mirim.family.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "USER", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원 생성 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "409", description = "존재하는 Email일 경우")
    })
    @Operation(tags = "USER", summary = "회원 생성", description = "회원을 생성하는 API")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid CreateUserRequest createUserRequest,
                           BindingResult bindingResult) {
        userService.createUser(createUserRequest, bindingResult);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 로그인인 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "403", description = "회원 정보가 일치하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "Email이 존재하지 않을 경우")
    })
    @Operation(tags = "USER", summary = "회원 로그인", description = "로그인하는 API")
    @PostMapping("/login")
    public LoginUserResponse loginUser(@RequestBody @Valid LoginUserRequest loginUserRequest,
                                       BindingResult bindingResult) {
        return userService.loginUser(loginUserRequest, bindingResult);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "403", description = "회원 정보가 일치하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "userId가 존재하지 않을 경우")
    })
    @Operation(tags = "USER", summary = "회원 삭제", description = "회원을 삭제하는 API")
    @DeleteMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@ApiParam(value = "삭제할 회원 ID") @PathVariable long userId,
                           @RequestBody DeleteUserRequest deleteUserRequest) {
        userService.deleteUser(userId, deleteUserRequest);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원 비밀번호 갱신 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "403", description = "회원 정보가 일치하지 않을 경우"),
            @ApiResponse(responseCode = "409", description = "변경할 비밀번호와 확인 비밀번호가 일치하지 않을 경우")
    })
    @Operation(tags = "USER", summary = "회원 비밀번호 변경", description = "회원의 비밀번호를 변경하는 API")
    @PutMapping("/{userId}/password")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateUserPassword(@ApiParam(value = "비밀번호를 변경할 회원 ID") @PathVariable long userId,
                                   @RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest,
                                   BindingResult bindingResult) {
        userService.updateUserPassword(userId, updateUserPasswordRequest, bindingResult);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원 정보 갱신 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "userId가 존재하지 않을 경우")
    })
    @Operation(tags = "USER", summary = "회원 정보 갱신", description = "비밀번호를 제외한 회원의 정보를 갱신하는 API")
    @PutMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateUser(@ApiParam(value = "정보를 갱신할 회원 ID") @PathVariable long userId,
                           @RequestBody @Valid UpdateUserRequest updateUserRequest,
                           BindingResult bindingResult) {
        userService.updateUser(userId, updateUserRequest, bindingResult);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 상세 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "date 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "userId가 존재하지 않을 경우")
    })
    @Operation(tags = "USER", summary = "회원 상세 정보 조회", description = "회원의 상세 정보와 자신이 받은 이전달의 왕 정보를 조회하는 API")
    @GetMapping("/{userId}")
    public UserFindResponse findUserInfo(@ApiParam(value = "상세 정보를 조회할 회원 ID") @PathVariable long userId,
                                         @ApiParam(value = "상세 정보를 조회할 년-월") @RequestParam String date) {
        return userService.findUserInfo(userId, date);
    }
}
