package kr.hs.mirim.family.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.hs.mirim.family.dto.request.*;
import kr.hs.mirim.family.entity.ingredient.IngredientCategory;
import kr.hs.mirim.family.entity.ingredient.IngredientSaveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class IngredientControllerTest {

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
    void 식재료_생성_201() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개", "냠냠", LocalDate.of(2022, 4, 11), LocalDate.of(2023, 4, 11));

        mvc.perform(post("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    void 식재료_생성_400_request_형식() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "1개", "냠냠", LocalDate.of(2022, 4, 11), LocalDate.of(2023, 4, 11));

        mvc.perform(post("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("유효하지 않은 형식입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_생성_409_유효기간() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개", "냠냠", LocalDate.of(2022, 4, 11), LocalDate.of(2021, 4, 11));

        mvc.perform(post("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message").value("유통 기한이 구매 날짜보다 먼저입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_생성_404_그룹_아이디_없음() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개", "냠냠", LocalDate.of(2022, 4, 11), LocalDate.of(2023, 4, 11));

        mvc.perform(post("/groups/-1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("존재하지 않는 그룹입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_생성_409_개수_0() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "0g", "냠냠", LocalDate.of(2022, 4, 11), LocalDate.of(2023, 4, 11));

        mvc.perform(post("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message").value("식재료의 수가 0입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_전체조회_200_saveType_required_true() throws Exception {
        mvc.perform(get("/groups/1/ingredients").param("saveType", "FRIDGE"))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    void 식재료_전체조회_200_saveType_required_false() throws Exception {
        mvc.perform(get("/groups/1/ingredients"))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    void 식재료_전체조회_500_saveType_에러() throws Exception {
        mvc.perform(get("/groups/1/ingredients").param("saveType", "fridgee"))
                .andExpect(status().is(500))
                .andDo(print());
    }

    @Test
    void 식재료_전체조회_404_그룹아이디_없음() throws Exception {
        mvc.perform(get("/groups/-1/ingredients"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("존재하지 않는 그룹입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_업데이트_204() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개", "냠냠", LocalDate.of(2022, 4, 11), LocalDate.of(2023, 4, 11));

        mvc.perform(put("/groups/1/ingredients/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(204))
                .andDo(print());
    }

    @Test
    void 식재료_업데이트_204_식재료_0일_경우() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "0개", "냠냠", LocalDate.of(2022, 4, 11), LocalDate.of(2023, 4, 11));

        mvc.perform(put("/groups/1/ingredients/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(204))
                .andDo(print());
    }

    @Test
    void 식재료_업데이트_404_그룹아이디_없음() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개", "냠냠", LocalDate.of(2022, 4, 11), LocalDate.of(2023, 4, 11));

        mvc.perform(put("/groups/-1/ingredients/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("존재하지 않는 그룹입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_업데이트_404_식재료_없음() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개", "냠냠", LocalDate.of(2022, 4, 11), LocalDate.of(2023, 4, 11));

        mvc.perform(put("/groups/1/ingredients/-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("존재하지 않는 식재료입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_업데이트_400_request_형식() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "", "냠냠", LocalDate.of(2022, 4, 11), LocalDate.of(2023, 4, 11));

        mvc.perform(put("/groups/1/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("유효하지 않은 형식입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_업데이트_404_그룹안에_식재료_없음() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개", "냠냠", LocalDate.of(2022, 4, 11), LocalDate.of(2023, 4, 11));

        mvc.perform(put("/groups/2/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("해당 그룹에 식재료가 없습니다."))
                .andDo(print());
    }

    @Test
    void 식재료_업데이트_409_유효기간() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개", "냠냠", LocalDate.of(2022, 4, 11), LocalDate.of(2013, 4, 11));

        mvc.perform(put("/groups/1/ingredients/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.message").value("유통 기한이 구매 날짜보다 먼저입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_삭제_204() throws Exception {
        List<DeleteIngredientRequest> data = new ArrayList<>();
        data.add(new DeleteIngredientRequest(1));
        data.add(new DeleteIngredientRequest(2));

        mvc.perform(delete("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().is(204))
                .andDo(print());
    }

    @Test
    void 식재료_삭제_404_그룹아이디_없음() throws Exception {
        List<DeleteIngredientRequest> data = new ArrayList<>();
        data.add(new DeleteIngredientRequest(1));
        data.add(new DeleteIngredientRequest(2));

        mvc.perform(delete("/groups/-1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("존재하지 않는 그룹입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_삭제_404_식재료아이디_없음() throws Exception {
        List<DeleteIngredientRequest> data = new ArrayList<>();
        data.add(new DeleteIngredientRequest(-1));

        mvc.perform(delete("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("존재하지 않는 식재료입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_삭제_404_그룹안에_식재료없음() throws Exception {
        List<DeleteIngredientRequest> data = new ArrayList<>();
        data.add(new DeleteIngredientRequest(1));

        mvc.perform(delete("/groups/2/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("해당 그룹에 식재료가 없습니다."))
                .andDo(print());
    }

    @Test
    void 식재료_수량_갱신_204() throws Exception {
        List<UpdateIngredientCountRequest> data = new ArrayList<>();
        data.add(new UpdateIngredientCountRequest(1, "2g"));
        data.add(new UpdateIngredientCountRequest(2, "0팩"));

        mvc.perform(put("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().is(204))
                .andDo(print());
    }

    @Test
    void 식재료_수량_갱신_400_request_형식() throws Exception {
        List<UpdateIngredientCountRequest> data = new ArrayList<>();
        data.add(new UpdateIngredientCountRequest(1,""));

        mvc.perform(put("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message").value("유효하지 않은 형식입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_수량_갱신_404_그룹아이디_없음() throws Exception {
        List<UpdateIngredientCountRequest> data = new ArrayList<>();
        data.add(new UpdateIngredientCountRequest(1, "2g"));
        data.add(new UpdateIngredientCountRequest(2, "0개"));

        mvc.perform(put("/groups/-1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("존재하지 않는 그룹입니다."))
                .andDo(print());
    }

    @Test
    void 식재료_수량_갱신_404_그룹안에_식재료없음() throws Exception {
        List<UpdateIngredientCountRequest> data = new ArrayList<>();
        data.add(new UpdateIngredientCountRequest(1, "2g"));

        mvc.perform(put("/groups/2/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("해당 그룹에 식재료가 없습니다."))
                .andDo(print());
    }

    @Test
    void 식재료_수량_갱신_404_식재료없음() throws Exception {
        List<UpdateIngredientCountRequest> data = new ArrayList<>();
        data.add(new UpdateIngredientCountRequest(-1, "2g"));

        mvc.perform(put("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message").value("존재하지 않는 식재료입니다."))
                .andDo(print());
    }
}