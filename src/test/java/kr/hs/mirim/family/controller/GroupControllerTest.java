package kr.hs.mirim.family.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hs.mirim.family.dto.request.CreateGroupRequest;
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

}