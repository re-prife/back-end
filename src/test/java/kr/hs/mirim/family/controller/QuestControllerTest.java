package kr.hs.mirim.family.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hs.mirim.family.dto.request.QuestRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class QuestControllerTest {

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


    @Transactional
    @Test
    void 심부름_생성_200() throws Exception {
        QuestRequest request = new QuestRequest("quest test", "quest 200 code test");

        mockMvc.perform(post("/groups/1/quests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Transactional
    @Test
    void 심부름_생성_400() throws Exception {
        QuestRequest request = new QuestRequest("", "");

        mockMvc.perform(post("/groups/1/quests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    void 심부름_생성_404_by_group() throws Exception {
        QuestRequest request = new QuestRequest("quest test", "quest 404 code test");

        mockMvc.perform(post("/groups/100/quests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_생성_404_by_user() throws Exception {
        QuestRequest request = new QuestRequest("quest test", "quest 404 code test");

        mockMvc.perform(post("/groups/1/quests/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void 심부름_조회_200() throws Exception {
        mockMvc.perform(get("/groups/1/quests"))
                .andExpect(status().isOk());
    }

    @Test
    void 심부름_조회_404() throws Exception {
        mockMvc.perform(get("/groups/100/quests"))
                .andExpect(status().isNotFound());
    }

    @Test
    void 심부름_상세_조회_200() throws Exception {
        mockMvc.perform(get("/groups/1/quests/1"))
                .andExpect(status().isOk());
    }

    @Test
    void 심부름_상세_조회_404_by_group() throws Exception {
        mockMvc.perform(get("/groups/100/quests/1"))
                .andExpect(status().isNotFound());
    }

    //{"status":404,"message":"해당 그룹에 심부름이 존재하지 않습니다."}
    @Test
    void 심부름_상세_조회_404_by_quest() throws Exception {
        mockMvc.perform(get("/groups/1/quests/100"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_갱신_200() throws Exception {
        QuestRequest request = new QuestRequest("quest test", "quest 200 code test");

        mockMvc.perform(put("/groups/1/quests/2?requesterId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Transactional
    @Test
    void 심부름_갱신_400() throws Exception {
        QuestRequest request = new QuestRequest("", "quest 400 code test");

        mockMvc.perform(put("/groups/1/quests/2?requesterId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    void 심부름_갱신_404_by_group() throws Exception {
        QuestRequest request = new QuestRequest("quest test", "quest 404 code test");

        mockMvc.perform(put("/groups/100/quests/2?requesterId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_갱신_404_by_quest() throws Exception {
        QuestRequest request = new QuestRequest("quest test", "quest 404 code test");

        mockMvc.perform(put("/groups/1/quests/100?requesterId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_갱신_404_by_requester() throws Exception {
        QuestRequest request = new QuestRequest("quest test", "quest 404 code test");

        mockMvc.perform(put("/groups/1/quests/2?requesterId=100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_갱신_405() throws Exception {
        QuestRequest request = new QuestRequest("quest test", "quest 405 code test");

        mockMvc.perform(put("/groups/1/quests/1?requesterId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isMethodNotAllowed());
    }

    @Transactional
    @Test
    void 심부름_갱신_409() throws Exception {
        QuestRequest request = new QuestRequest("quest test", "quest 409 code test");

        mockMvc.perform(put("/groups/1/quests/2?requesterId=3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Transactional
    @Test
    void 심부름_삭제_204() throws Exception {
        mockMvc.perform(delete("/groups/1/quests/2?userId=1"))
                .andExpect(status().isNoContent());
    }

    @Transactional
    @Test
    void 심부름_삭제_404_by_group() throws Exception {
        mockMvc.perform(delete("/groups/100/quests/2?userId=1"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_삭제_404_by_quest() throws Exception {
        mockMvc.perform(delete("/groups/1/quests/100?userId=1"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_삭제_404_by_user() throws Exception {
        mockMvc.perform(delete("/groups/1/quests/2?userId=100"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_삭제_404_by_user_in_group() throws Exception {
        mockMvc.perform(delete("/groups/1/quests/8?userId=100"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_삭제_405() throws Exception {
        mockMvc.perform(delete("/groups/1/quests/1?userId=1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Transactional
    @Test
    void 심부름_삭제_409() throws Exception {
        mockMvc.perform(delete("/groups/1/quests/2?userId=2"))
                .andExpect(status().isConflict());
    }

    @Transactional
    @Test
    void 심부름_수락_및_취소_200() throws Exception {
        mockMvc.perform(put("/groups/1/quests/2/acceptor/2"))
                .andExpect(status().isOk());
    }

    @Transactional
    @Test
    void 심부름_수락_및_취소_200_by_accepter() throws Exception {
        mockMvc.perform(put("/groups/1/quests/3/acceptor/3"))
                .andExpect(status().isOk());
    }
    @Transactional
    @Test
    void 심부름_수락_및_취소_404_by_group() throws Exception {
        mockMvc.perform(put("/groups/100/quests/2/acceptor/2"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_수락_및_취소_404_by_quest() throws Exception {
        mockMvc.perform(put("/groups/1/quests/100/acceptor/2"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_수락_및_취소_404_by_user() throws Exception {
        mockMvc.perform(put("/groups/1/quests/2/acceptor/100"))
                .andExpect(status().isNotFound());
    }

    //{"status":409,"message":"완료된 심부름입니다."}
    @Transactional
    @Test
    void 심부름_수락_및_취소_409_by_already_acceptor() throws Exception {
        mockMvc.perform(put("/groups/1/quests/1/acceptor/4"))
                .andExpect(status().isConflict());
    }

    //{"status":409,"message":"심부름 수락자가 아닌 사람은 수락을 취소할 수 없습니다."}
    @Transactional
    @Test
    void 심부름_수락_및_취소_409_completion() throws Exception {
        mockMvc.perform(put("/groups/1/quests/3/acceptor/4"))
                .andExpect(status().isConflict());
    }

    //{"status":409,"message":"심부름을 추가한 사람은 수락할 수 없습니다."}
    @Transactional
    @Test
    void 심부름_수락_및_취소_409_by_requester() throws Exception {
        mockMvc.perform(put("/groups/1/quests/2/acceptor/1"))
                .andExpect(status().isConflict());
    }

    @Transactional
    @Test
    void 심부름_완료_200() throws Exception {
        mockMvc.perform(put("/groups/1/quests/3/complete/1"))
                .andExpect(status().isOk());
    }

    @Transactional
    @Test
    void 심부름_완료_404_by_group() throws Exception {
        mockMvc.perform(put("/groups/100/quests/2/complete/1"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_완료_404_by_quest() throws Exception {
        mockMvc.perform(put("/groups/1/quests/100/complete/1"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_완료_404_by_user() throws Exception {
        mockMvc.perform(put("/groups/1/quests/2/complete/100"))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 심부름_완료_409_by_not_requester() throws Exception {
        mockMvc.perform(put("/groups/1/quests/2/complete/2"))
                .andExpect(status().isConflict());
    }

    @Transactional
    @Test
    void 심부름_완료_409_not_accepter() throws Exception {
        mockMvc.perform(put("/groups/1/quests/2/complete/1"))
                .andExpect(status().isConflict());
    }
}