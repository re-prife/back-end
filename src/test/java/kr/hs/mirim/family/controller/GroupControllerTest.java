package kr.hs.mirim.family.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hs.mirim.family.dto.request.CreateGroupRequest;
import kr.hs.mirim.family.dto.request.JoinGroupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
class GroupControllerTest {
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
    void 그룹_생성_200() throws Exception{
        CreateGroupRequest request = new CreateGroupRequest("그룹 이름");

        mockMvc.perform(post("/groups/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    // 그룹 초대 코드가 빈 값이 거나 request body가 없을 경우
    @Transactional
    @Test
    void 그룹_생성_400() throws Exception{
        CreateGroupRequest request = new CreateGroupRequest();

        mockMvc.perform(post("/groups/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    void 그룹_생성_404() throws Exception{
        CreateGroupRequest request = new CreateGroupRequest("그룹 이름");

        mockMvc.perform(post("/groups/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 그룹_생성_409() throws Exception{
        CreateGroupRequest request = new CreateGroupRequest("그룹 이름");

        mockMvc.perform(post("/groups/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Transactional
    @Test
    void 그룹_가입_200() throws Exception{
        JoinGroupRequest request = new JoinGroupRequest("mon0516");

        mockMvc.perform(post("/groups/join/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    // 그룹 초대 코드가 빈 값이 거나 request body가 없을 경우
    @Transactional
    @Test
    void 그룹_가입_400() throws Exception{
        JoinGroupRequest request = new JoinGroupRequest("");

        mockMvc.perform(post("/groups/join/6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    void 그룹_가입_404() throws Exception{
        JoinGroupRequest request = new JoinGroupRequest("mon0516");

        mockMvc.perform(post("/groups/join/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void 그룹_가입_409() throws Exception{
        JoinGroupRequest request = new JoinGroupRequest("mon0516");

        mockMvc.perform(post("/groups/join/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isConflict());
    }



}