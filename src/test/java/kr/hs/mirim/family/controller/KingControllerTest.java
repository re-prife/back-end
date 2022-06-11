package kr.hs.mirim.family.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class KingControllerTest {
    MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }


    @Test
    void userKingInfo_200() throws Exception {
        mvc.perform(get("/groups/1/kings")
                .param("date","2022-06"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void userKingInfo_400() throws Exception {
        mvc.perform(get("/groups/1/kings")
                        .param("date","2022-06-22"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("잘못된 날짜 형식입니다."))
                .andDo(print());
    }

    @Test
    void userKingInfo_404() throws Exception {
        mvc.perform(get("/groups/1767/kings")
                        .param("date","2022-06"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("존재하지 않는 그룹입니다."))
                .andDo(print());

    }
}