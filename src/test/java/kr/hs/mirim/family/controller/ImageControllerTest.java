package kr.hs.mirim.family.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


import java.io.FileInputStream;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class ImageControllerTest {
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
    void userImageUpdate_200() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png",
                new FileInputStream("src/test/resources/image/test.png"));

        mvc.perform(multipart("/uploads/users/1")
                        .file(file))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    void userImageUpdate_404() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png",
                new FileInputStream("src/test/resources/image/test.png"));

        mvc.perform(multipart("/uploads/users/-1")
                        .file(file))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("해당하는 유저가 없습니다."))
                .andDo(print());
    }

    @Test
    void ingredientImageUpdate_200() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png",
                new FileInputStream("src/test/resources/image/test.png"));

        mvc.perform(multipart("/uploads/ingredients/1")
                        .file(file))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    void ingredientImageUpdate_404() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png",
                new FileInputStream("src/test/resources/image/test.png"));

        mvc.perform(multipart("/uploads/ingredients/-1")
                        .file(file))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("해당하는 식재료가 없습니다."))
                .andDo(print());
    }

}