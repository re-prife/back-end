package kr.hs.mirim.family.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hs.mirim.family.dto.request.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(print())
                .build();
    }

    //회원이 정상적으로 생성(가입)된 경우 - 201
    @Test
    void 회원가입_성공_201() throws Exception {
        String userName = "Testor";
        String userNickname = "시험중";
        String userEmail = "Test@gamil.com";
        String userPassword = "test1234";
        CreateUserRequest createUserRequest = new CreateUserRequest(userName, userNickname, userEmail, userPassword);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated());
    }

    //이메일 형식이 맞지 않는 경우 - 400
    @Test
    void 회원가입_이메일_400() throws Exception {
        String userName = "Testor";
        String userNickname = "시험중";
        String userEmail = "Test";  //이메일 형식이 맞지 않는 경우
        String userPassword = "test1234";

        CreateUserRequest createUserRequest = new CreateUserRequest(userName, userNickname, userEmail, userPassword);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }

    //비밀번호 형식이 맞지 않는 경우 - 400
    @Test
    void 회원가입_비밀번호_400() throws Exception {
        String userName = "Testor";
        String userNickname = "시험중";
        String userEmail = "Test@gmail.com";
        String userPassword = "test";   //password 형식이 맞지 않는 경우(8자리 이상)

        CreateUserRequest createUserRequest = new CreateUserRequest(userName, userNickname, userEmail, userPassword);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }

    //값이 모두 들어오지 않은 경우 - 400
    @Test
    void 회원가입_형식_400() throws Exception {
        String userName = "";
        String userNickname = "";
        String userEmail = "";
        String userPassword = "";

        CreateUserRequest createUserRequest = new CreateUserRequest(userName, userNickname, userEmail, userPassword);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }

    //이미 존재하는 회원이 회원가입하는 경우 - 409
    @Test
    void 회원가입_회원중복_409() throws Exception {
        String userName = "Min J";
        String userNickname = "취준생";
        String userEmail = "m04j00@gmail.com";
        String userPassword = "doremisol";

        CreateUserRequest createUserRequest1 = new CreateUserRequest(userName, userNickname, userEmail, userPassword);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createUserRequest1)))
                .andExpect(status().isConflict());
    }

    //로그인이 성공적으로 실행된 경우 - 200
    @Test
    void 로그인_성공_200() throws Exception {
        String userEmail = "m04j00@gmail.com";
        String userPassword = "doremisol";
        LoginUserRequest loginUserRequest = new LoginUserRequest(userEmail, userPassword);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserRequest)))
                .andExpect(status().isOk());
    }

    //로그인시 이메일 형식이 옳지 않은 경우 - 400
    @Test
    void 로그인_이메일형식_400() throws Exception {
        String userEmail = "Test";  //이메일 형식이 옳지 않음
        String userPassword = "doremisol";
        LoginUserRequest loginUserRequest = new LoginUserRequest(userEmail, userPassword);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserRequest)))
                .andExpect(status().isBadRequest());
    }

    //로그인시 값이 없는 경우 - 400
    @Test
    void 로그인_값없음_400() throws Exception {
        String userEmail = "";
        String userPassword = "";
        LoginUserRequest loginUserRequest = new LoginUserRequest(userEmail, userPassword);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserRequest)))
                .andExpect(status().isBadRequest());
    }

    //비밀번호와 이메일이 일치하지 않는 경우 - 403
    @Test
    void 로그인_일치하지않음_403() throws Exception {
        String userEmail = "m04j00@gmail.com";
        String userPassword = "idontknow";
        LoginUserRequest loginUserRequest = new LoginUserRequest(userEmail, userPassword);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserRequest)))
                .andExpect(status().isForbidden());
    }

    //존재하지 않는 이메일(회원)일 경우 - 404
    @Test
    void 로그인_존재하지않는_회원_404() throws Exception {
        String userEmail = "none@gmail.com";
        String userPassword = "doremisol";
        LoginUserRequest loginUserRequest = new LoginUserRequest(userEmail, userPassword);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserRequest)))
                .andExpect(status().isNotFound());
    }

    //그룹에 참여하지 않은 회원일 경우 - 404
    @Test
    void 로그인_그룹없는_회원_404() throws Exception {
        String userEmail = "freemily@gmail.com";
        String userPassword = "doremisol";
        LoginUserRequest loginUserRequest = new LoginUserRequest(userEmail, userPassword);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginUserRequest)))
                .andExpect(status().isNotFound());
    }

    //회원탈퇴가 성공적으로 완료된 경우 - 204
    @Test
    void 회원탈퇴_성공_204() throws Exception {
        String userPassword = "doremisol";

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest(userPassword);

        mockMvc.perform(delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deleteUserRequest)))
                .andExpect(status().isNoContent());
    }

    //회원의 비밀번호가 일치하지 않는 경우 - 403
    @Test
    void 회원탈퇴_비밀번호불일치_403() throws Exception {
        String userPassword = "idontknow";

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest(userPassword);

        mockMvc.perform(delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deleteUserRequest)))
                .andExpect(status().isForbidden());
    }

    // 존재하지 않는 id인 경우 - 404
    @Test
    void 회원탈퇴_존재하지않는회원_404() throws Exception {
        String userPassword = "doremisol";

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest(userPassword);

        mockMvc.perform(delete("/users/-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(deleteUserRequest)))
                .andExpect(status().isNotFound());
    }

    //회원 수정이 성공적으로 완료된 경우 - 204
    @Test
    void 회원수정_성공_204() throws Exception {
        String userName = "MJ";
        String userNickname = "MJCEO";

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(userName, userNickname);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateUserRequest)))
                .andExpect(status().isNoContent());
    }

    //값이 들어오지 않은 경우(형식이 맞지 않는경우) - 400
    @Test
    void 회원수정_값없음_400() throws Exception {
        String userName = "";
        String userNickname = "";

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(userName, userNickname);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateUserRequest)))
                .andExpect(status().isBadRequest());
    }

    //해당 아이디의 회원이 존재하지 않는 경우 - 404
    @Test
    void 회원수정_존재하지않는회원_404() throws Exception {
        String userName = "MJ";
        String userNickname = "MJCEO";

        UpdateUserRequest updateUserRequest = new UpdateUserRequest(userName, userNickname);

        mockMvc.perform(put("/users/-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateUserRequest)))
                .andExpect(status().isNotFound());
    }

    //비밀번호 변경이 성공적으로 수행된 경우 - 204
    @Test
    void 비밀번호변경_204() throws Exception {
        String userPassword = "doremisol";
        String userNewPassword = "doremisolsol";
        String userNewPasswordCheck = "doremisolsol";

        UpdateUserPasswordRequest updateUserPasswordRequest = new UpdateUserPasswordRequest(userPassword, userNewPassword, userNewPasswordCheck);

        mockMvc.perform(put("/users/1/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateUserPasswordRequest)))
                .andExpect(status().isNoContent());
    }

    //회원의 현재 비밀번호가 맞지 않을때 - 403
    @Test
    void 비밀번호변경_회원정보틀림_403() throws Exception {
        String userPassword = "test0000";
        String userNewPassword = "doremisolsol";
        String userNewPasswordCheck = "doremisolsol";

        UpdateUserPasswordRequest updateUserPasswordRequest = new UpdateUserPasswordRequest(userPassword, userNewPassword, userNewPasswordCheck);

        mockMvc.perform(put("/users/1/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateUserPasswordRequest)))
                .andExpect(status().isForbidden());
    }

    //존재하지 않은 이메일(회원)일 경우 - 404
    @Test
    void 비밀번호변경_존재하지않는회원_404() throws Exception {
        String userPassword = "doremisol";
        String userNewPassword = "doremisolsol";
        String userNewPasswordCheck = "doremisolsol";

        UpdateUserPasswordRequest updateUserPasswordRequest = new UpdateUserPasswordRequest(userPassword, userNewPassword, userNewPasswordCheck);

        mockMvc.perform(put("/users/-1/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateUserPasswordRequest)))
                .andExpect(status().isNotFound());
    }

    //새로운 비밀번호와 비밀번호 확인이 맞지 않을때 - 409
    @Test
    void 비밀번호변경_비밀번호확인틀림_409() throws Exception {
        String userPassword = "doremisol";
        String userNewPassword = "doremisolsol";
        String userNewPasswordCheck = "doremisol123";

        UpdateUserPasswordRequest updateUserPasswordRequest = new UpdateUserPasswordRequest(userPassword, userNewPassword, userNewPasswordCheck);

        mockMvc.perform(put("/users/1/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateUserPasswordRequest)))
                .andExpect(status().isConflict());
    }

    //회원 조회가 성공적으로 완료된 경우 - 200
    @Test
    void 회원조회_200() throws Exception {
        mockMvc.perform(get("/users/1?date=2022-07")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //회원 조회에 심부름왕이 있고, 성공적으로 완료된 경우 - 200
    @Test
    void 회원조회_심부름왕맞음_200() throws Exception {
        mockMvc.perform(get("/users/3?date=2022-07")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //회원 조회에 심부름왕이 있지만, 해당 회원은 아니고 성공적으로 완료된 경우 - 200
    @Test
    void 회원조회_심부름왕아님_200() throws Exception {
        mockMvc.perform(get("/users/1?date=2022-07")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //왕이 없을때, 회원 조회가 성공적으로 완료된 경우 - 200
    @Test
    void 회원조회_왕없음_200() throws Exception {
        mockMvc.perform(get("/users/1?date=2022-06")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //조회시 날짜 형식이 틀릴 경우 - 400
    @Test
    void 회원조회_날짜형식_400() throws Exception {
        mockMvc.perform(get("/users/1?date=2022-7")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //존재하지 않는 회원일 경우 - 404
    @Test
    void 회원조회_회원없음_404() throws Exception {
        mockMvc.perform(get("/users/-1?date=2022-07")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
