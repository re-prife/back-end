package kr.hs.mirim.family.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.hs.mirim.family.dto.request.ChoreCertifyReactionRequest;
import kr.hs.mirim.family.dto.request.CreateChoreRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
class ChoreControllerTest {

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

    //집안일 생성이 성공적으로 실행된 경우 - 201
    @Test
    void 집안일당번_생성_성공_200() throws Exception {
        //Java version8
        String choreTitle = "계란사오기";
        String choreCategory = "SHOPPING";
        LocalDate choreDate = LocalDate.of(2030, 1, 1);
        long choreUserId = 1;

        CreateChoreRequest createUserRequest = new CreateChoreRequest(choreTitle, choreCategory, choreDate, choreUserId);

        mockMvc.perform(post("/groups/1/chores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated());

    }

    //집안일명이 존재하지 않는 경우 - 400
    @Test
    void 집안일당번_생성_값없음_400() throws Exception {
        String choreTitle = "";
        String choreCategory = "SHOPPING";
        LocalDate choreDate = LocalDate.of(2030, 1, 1);
        long choreUserId = 1;

        CreateChoreRequest createUserRequest = new CreateChoreRequest(choreTitle, choreCategory, choreDate, choreUserId);

        mockMvc.perform(post("/groups/1/chores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());
    }

    //그룹아이디가 존재하지 않는 그룹아이디인 경우 - 404
    @Test
    void 집안일당번_생성_그룹없음_404() throws Exception {
        String choreTitle = "계란 사오기";
        String choreCategory = "SHOPPING";
        LocalDate choreDate = LocalDate.of(2030, 1, 1);
        long choreUserId = 1;

        CreateChoreRequest createUserRequest = new CreateChoreRequest(choreTitle, choreCategory, choreDate, choreUserId);

        mockMvc.perform(post("/groups/-1/chores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(createUserRequest)))
                .andExpect(status().isNotFound());
    }

    //그룹아이디가 존재하지 않는 그룹아이디인 경우 - 404
    @Test
    void 집안일당번_생성_회원없음_404() throws Exception {
        String choreTitle = "계란 사오기";
        String choreCategory = "SHOPPING";
        LocalDate choreDate = LocalDate.of(2030, 1, 1);
        long choreUserId = -1;

        CreateChoreRequest createUserRequest = new CreateChoreRequest(choreTitle, choreCategory, choreDate, choreUserId);

        mockMvc.perform(post("/groups/1/chores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(createUserRequest)))
                .andExpect(status().isNotFound());
    }

    //해당 그룹 안에 존재하지 않는 회원인 경우 - 404
    @Test
    void 집안일당번_생성_그룹에회원없음_404() throws Exception {
        String choreTitle = "계란 사오기";
        String choreCategory = "SHOPPING";
        LocalDate choreDate = LocalDate.of(2030, 1, 1);
        long choreUserId = 1;

        CreateChoreRequest createUserRequest = new CreateChoreRequest(choreTitle, choreCategory, choreDate, choreUserId);

        mockMvc.perform(post("/groups/2/chores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(createUserRequest)))
                .andExpect(status().isNotFound());
    }

    //존재하지 않는 카테고리인 경우 - 404
    @Test
    void 집안일당번_생성_카테고리없음_404() throws Exception {
        String choreTitle = "계란 사오기";
        String choreCategory = "NONE";
        LocalDate choreDate = LocalDate.of(2030, 1, 1);
        long choreUserId = 1;

        CreateChoreRequest createUserRequest = new CreateChoreRequest(choreTitle, choreCategory, choreDate, choreUserId);

        mockMvc.perform(post("/groups/1/chores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(createUserRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void 집안일당번_이미존재_409() throws Exception {
        String choreTitle = "하늘이 요리하는 날";
        String choreCategory = "COOK";
        LocalDate choreDate = LocalDate.now();
        long choreUserId = 4;

        CreateChoreRequest createUserRequest = new CreateChoreRequest(choreTitle, choreCategory, choreDate, choreUserId);

        mockMvc.perform(post("/groups/1/chores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(createUserRequest)))
                .andExpect(status().isConflict());
    }

    //지난날짜에 대하여 집안일을 생성하는 경우 - 409
    @Test
    void 집안일당번_생성_지난날짜_409() throws Exception {
        String choreTitle = "계란 사오기";
        String choreCategory = "SHOPPING";
        LocalDate choreDate = LocalDate.of(2020, 1, 1);
        long choreUserId = 1;

        CreateChoreRequest createUserRequest = new CreateChoreRequest(choreTitle, choreCategory, choreDate, choreUserId);

        mockMvc.perform(post("/groups/1/chores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(createUserRequest)))
                .andExpect(status().isConflict());
    }

    //해당 하루의 집안일 당번 조회가 성공적으로 실행된 경우 - 200
    @Test
    void 집안일당번_하루조회_성공_200() throws Exception {
        mockMvc.perform(get("/groups/1/chores/one-day?date=2022-06-10"))
                .andExpect(status().isOk());
    }

    //하루 집안일 당번 조회시 날짜 형식이 옳지 않은 경우 - 400
    @Test
    void 집안일당번_하루조회_날짜형식_400() throws Exception {
        mockMvc.perform(get("/groups/1/chores/one-day?date=2022.06.10"))
                .andExpect(status().isBadRequest());
    }

    //하루 집안일 당번 조회시 그룹이 존재하지 않는 경우 - 404
    @Test
    void 집안일당번_하루조회_그룹없음_404() throws Exception {
        mockMvc.perform(get("/groups/-1/chores/one-day?date=2022-06-10"))
                .andExpect(status().isNotFound());
    }

    //한달 집안일 당번 조회가 성공적으로 수행된 경우 - 200
    @Test
    void 집안일당번_한달조회_성공_200() throws Exception {
        mockMvc.perform(get("/groups/1/chores?date=2022-06"))
                .andExpect(status().isOk());
    }

    //한달 집안일 당번 조회시 날짜 형식이 옳지 않은 경우 - 400
    @Test
    void 집안일당번_한달조회_날짜형식_400() throws Exception {
        mockMvc.perform(get("/groups/1/chores?date=2022.06"))
                .andExpect(status().isBadRequest());
    }

    //한달 집안일 당번 조회시 그룹이 존재하지 않는 경우 - 404
    @Test
    void 집안일당번_한달조회_그룹없음_404() throws Exception {
        mockMvc.perform(get("/groups/-1/chores?date=2022-06"))
                .andExpect(status().isNotFound());
    }

    //집안일을 한 후 인증요청(REQUEST)하는 기능이 성공적으로 실행된 경우 - 200
    @Test
    void 집안일_인증요청_성공_200() throws Exception {
        mockMvc.perform(put("/groups/1/chores/1/certify"))
                .andExpect(status().isOk());
    }

    //집안일 인증요청하는 그룹이 존재하지 않는 경우 - 404
    @Test
    void 집안일_인증요청_그룹없음_404() throws Exception {
        mockMvc.perform(put("/groups/-1/chores/1/certify"))
                .andExpect(status().isNotFound());
    }

    //인증요청하는 집안일이 존재하지 않는 경우 - 404
    @Test
    void 집안일_인증요청_집안일없음_404() throws Exception {
        mockMvc.perform(put("/groups/1/chores/-1/certify"))
                .andExpect(status().isNotFound());
    }

    //해당 그룹내에 존재하는 집안일이 아닐 경우 - 404
    @Test
    void 집안일_인증요청_그룹에집안일없음_404() throws Exception {
        mockMvc.perform(put("/groups/2/chores/1/certify"))
                .andExpect(status().isNotFound());
    }

    //집안일 인증 요청을 한 집안일이 이미 실패(FAIL)상태인 경우 - 409
    @Test
    void 집안일_인증요청_실패상태_409() throws Exception {
        mockMvc.perform(put("/groups/1/chores/12/certify"))
                .andExpect(status().isConflict());
    }

    //집안일 인증 요청을 한 집안일이 이미 끝난 집안일(SUCCESS, REQUEST)인 경우 - 409
    @Test
    void 집안일_인증요청_요청끝남_409() throws Exception {
        mockMvc.perform(put("/groups/1/chores/2/certify"))
                .andExpect(status().isConflict());
    }

    //집안일 현재 상태가 SUCCESS이면서, 지난집안일인 경우 - 409
    @Test
    void 집안일_인증요청_지난날짜_SUCCESS_상태_409() throws Exception{
        mockMvc.perform(put("/groups/1/chores/15/certify"))
                .andExpect(status().isConflict());
    }

    //집안일 현재 상태가 REQUEST이면서, 지난집안일인 경우 - 409
    @Test
    void 집안일_인증요청_REQUEST_지난날짜_FAIL_상태_409() throws Exception{
        mockMvc.perform(put("/groups/1/chores/14/certify"))
                .andExpect(status().isConflict());

    }

    //집안일 현재 상태가 BEFORE이면서, 지난집안일인 경우 - 409
    @Test
    void 집안일_인증요청_BEFORE_지난날짜_FAIL_상태_409() throws Exception{
        mockMvc.perform(put("/groups/1/chores/16/certify"))
                .andExpect(status().isConflict());

    }

    //인증 요청한 집안일에 대한 응답이 성공적으로 수행된 경우 - 200
    @Test
    void 집안일_인증응답_SUCCESS_성공_200() throws Exception {
        String reaction = "SUCCESS";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/13/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isOk());
    }

    //값이 아무것도 들어오지 않아 변경사항이 없고, 응답이 성공적으로 수행된 경우 - 200
    @Test
    void 집안일_인증응답_REQUEST이면서_값없음_성공_200() throws Exception {
        String reaction = "";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/13/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isOk());
    }

    //집안일 현재 상태가 SUCCESS이면서, 지난집안일인 경우 - 200
    @Test
    void 집안일_인증응답_지난날짜_SUCCESS_상태_200() throws Exception {
        String reaction = "SUCCESS";

        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/15/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isOk());
    }

    //집안일 인증에 대한 응답을 받았을때, 집안일의 상태가 아직 인증요청을 하지 않은 BEFORE상태인 경우 - 400
    @Test
    void 집안일_인증응답_BEFORE_상태_400() throws Exception {
        String reaction = "SUCCESS";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/1/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isBadRequest());
    }

    //집안일 인증에 대한 응답으로 실패(FAIL)을 반환하는 경우 - 403
    @Test
    void 집안일_인증응답_FAIL_상태반환_403() throws Exception {
        String reaction = "FAIL";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/13/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isForbidden());
    }

    //집안일 인증에 대한 응답을 하는 그룹이 존재하지 않는 경우 - 404
    @Test
    void 집안일_인증응답_그룹없음_404() throws Exception {
        String reaction = "SUCCESS";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/-1/chores/13/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isNotFound());
    }

    //집안일 인증에 대한 응답을 하는 집안일이 존재하지 않는 경우 - 404
    @Test
    void 집안일_인증응답_집안일없음_404() throws Exception {
        String reaction = "SUCCESS";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/-1/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isNotFound());
    }

    //집안일 인증에 대한 응답을 받는 집안일이 해당 그룹에 존재하지 않는 경우 - 404
    @Test
    void 집안일_인증응답_그룹에집안일없음_404() throws Exception {
        String reaction = "SUCCESS";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/2/chores/13/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isNotFound());
    }

    //집안일 인증에 대한 응답이 존재하지 않는 상태로 반환된 경우 - 404
    @Test
    void 집안일_인증응답_집안일상태없음_404() throws Exception {
        String reaction = "NONE";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/13/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isNotFound());
    }

    //집안일 인증에 대한 응답을 받는 집안일의 현 상태가 실패(FAIL)일 경우 - 409
    @Test
    void 집안일_인증응답_FAIL_상태_409() throws Exception {
        String reaction = "SUCCESS";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/12/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isConflict());
    }

    //집안일 인증에 대한 응답을 받는 집안일의 현 상태가 실패(FAIL)일 경우 - 409
    //집안일 현재 상태가 REQUEST이면서, 지난집안일인 경우 - 409
    @Test
    void 집안일_인증응답_REQUEST_지난날짜_FAIL_상태_409() throws Exception {
        String reaction = "SUCCESS";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/14/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isConflict());
    }

    //현재상태가 SUCCESS인데, 인증 응답으로 SUCCESS를 받은 경우 - 409
    @Test
    void 집안일_인증응답_SUCCESS이면서_SUCCESS요청_409() throws Exception {
        String reaction = "SUCCESS";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/2/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isConflict());
    }

    //집안일 인증에 대한 응답을 받는 집안일의 현 상태가 실패(FAIL)일 경우 - 409
    //집안일 현재 상태가 BEFORE이면서, 지난집안일인 경우 - 409
    @Test
    void 집안일_인증응답_BEFORE_지난날짜_FAIL_상태_409() throws Exception {
        String reaction = "SUCCESS";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/16/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isConflict());
    }

    //집안일 인증에 대한 응답을 받는 집안일의 현 상태가 인증요청전(BEFORE)일 경우 - 409
    @Test
    void 집안일_인증응답_BEFORE_상태반환_409() throws Exception {
        String reaction = "BEFORE";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/13/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isConflict());
    }

    //집안일 인증에 대한 응답을 받는 집안일의 현 상태 이미 성공(SUCCESS)일 경우 - 409
    @Test
    void 집안일_인증응답_끝난집안일_409() throws Exception {
        String reaction = "REQUEST";
        ChoreCertifyReactionRequest choreCertifyReactionRequest = new ChoreCertifyReactionRequest(reaction);
        mockMvc.perform(put("/groups/1/chores/2/reaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(choreCertifyReactionRequest)))
                .andExpect(status().isConflict());
    }

    //집안일 삭제가 성공적으로 수행된 경우 - 200
    @Test
    void 집안일_삭제_성공_200() throws Exception {
        mockMvc.perform(delete("/groups/1/chores/1"))
                .andExpect(status().isNoContent());
    }

    //집안일을 삭제할때 그룹이 존재하지 않는 경우 - 404
    @Test
    void 집안일_삭제_그룹없음_404() throws Exception {
        mockMvc.perform(delete("/groups/-1/chores/1"))
                .andExpect(status().isNotFound());
    }

    //집안일을 삭제할때 집안일이 존재하지 않는 경우 - 404
    @Test
    void 집안일_삭제_집안일없음_404() throws Exception {
        mockMvc.perform(delete("/groups/1/chores/-1"))
                .andExpect(status().isNotFound());
    }

    //삭제할 집안일이 해당 그룹에 존재하지 않을 경우 - 404
    @Test
    void 집안일_삭제_그룹에집안일없음_404() throws Exception {
        mockMvc.perform(delete("/groups/2/chores/1"))
                .andExpect(status().isNotFound());
    }

    //삭제하려는 집안일이 이미 끝난 집안일(SUCCESS)일 경우 - 405
    @Test
    void 집안일_삭제_이미끝난집안일_SUCCESS_405() throws Exception {
        mockMvc.perform(delete("/groups/1/chores/2"))
                .andExpect(status().isMethodNotAllowed());
    }

    //삭제하려는 집안일이 이미 끝난 집안일(FAIL)일 경우 - 405
    @Test
    void 집안일_삭제_이미끝난집안일_FAIL_405() throws Exception {
        mockMvc.perform(delete("/groups/1/chores/12"))
                .andExpect(status().isMethodNotAllowed());
    }

}