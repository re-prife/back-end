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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class IngredientControllerTest {

    MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp(){
        mvc= MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void 식재료_생성_201() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개","냠냠",LocalDate.of(2022,4,11) ,LocalDate.of(2023,4,11));

        mvc.perform(post("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(201))
                .andDo(print());
    }
    @Test
    void 식재료_생성_400_request_형식() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "","냠냠",LocalDate.of(2022,4,11) ,LocalDate.of(2023,4,11));

        mvc.perform(post("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(400))
                .andDo(print());
    }

    @Test
    void 식재료_생성_409_유효기간() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개","냠냠",LocalDate.of(2022,4,11) ,LocalDate.of(2021,4,11));

        mvc.perform(post("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(409))
                .andDo(print());
    }

    @Test
    void 식재료_생성_404_그룹_아이디_없음() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개","냠냠",LocalDate.of(2022,4,11) ,LocalDate.of(2023,4,11));

        mvc.perform(post("/groups/10000/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(404))
                .andDo(print());
    }

    @Test
    void 식재료_생성_409_개수_0() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "0g","냠냠",LocalDate.of(2022,4,11) ,LocalDate.of(2023,4,11));

        mvc.perform(post("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(409))
                .andDo(print());
    }

    @Test
    void 식재료_전체조회_200_saveType_required_true() throws Exception {
        mvc.perform(get("/groups/1/ingredients").param("saveType","FRIDGE"))
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
        mvc.perform(get("/groups/1/ingredients").param("saveType","fridgee"))
                .andExpect(status().is(500))
                .andDo(print());
    }

    @Test
    void 식재료_전체조회_404_그룹아이디_없음() throws Exception {
        mvc.perform(get("/groups/10000/ingredients"))
                .andExpect(status().is(404))
                .andDo(print());
    }

    @Test
    void 식재료_업데이트_204() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개","냠냠",LocalDate.of(2022,4,11) ,LocalDate.of(2023,4,11));

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
                "0개","냠냠",LocalDate.of(2022,4,11) ,LocalDate.of(2023,4,11));

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
                "3개","냠냠",LocalDate.of(2022,4,11) ,LocalDate.of(2023,4,11));

        mvc.perform(put("/groups/1000/ingredients/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(404))
                .andDo(print());
    }
    @Test
    void 식재료_업데이트_400_request_형식() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "","냠냠",LocalDate.of(2022,4,11) ,LocalDate.of(2023,4,11));

        mvc.perform(put("/groups/1000/ingredients/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(400))
                .andDo(print());
    }

    @Test
    void 식재료_업데이트_404_그룹안에_식재료_없음() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        final IngredientRequest request = new IngredientRequest("감자", IngredientSaveType.FRIDGE, IngredientCategory.VEGGIE,
                "3개","냠냠",LocalDate.of(2022,4,11) ,LocalDate.of(2023,4,11));

        mvc.perform(put("/groups/2/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(404))
                .andDo(print());
    }

    @Test
    void 식재료_삭제_204() throws Exception {
        List<DeleteIngredientDataRequest> data = new ArrayList<>();
        data.add(new DeleteIngredientDataRequest(1));
        data.add(new DeleteIngredientDataRequest(2));
        DeleteIngredientRequest request = new DeleteIngredientRequest(data);

        mvc.perform(delete("/groups/1/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(204))
                .andDo(print());
    }

    @Test
    void 식재료_삭제_404_그룹아이디_없음() throws Exception {
        List<DeleteIngredientDataRequest> data = new ArrayList<>();
        data.add(new DeleteIngredientDataRequest(1));
        DeleteIngredientRequest request = new DeleteIngredientRequest(data);

        mvc.perform(delete("/groups/10/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(404))
                .andDo(print());
    }

    @Test
    void 식재료_삭제_404_식재료아이디_없음() throws Exception {
        List<DeleteIngredientDataRequest> data = new ArrayList<>();
        data.add(new DeleteIngredientDataRequest(551));
        DeleteIngredientRequest request = new DeleteIngredientRequest(data);

        mvc.perform(delete("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(404))
                .andDo(print());
    }
    @Test
    void 식재료_삭제_404_그룹안에_식재료없음() throws Exception {
        List<DeleteIngredientDataRequest> data = new ArrayList<>();
        data.add(new DeleteIngredientDataRequest(1));
        DeleteIngredientRequest request = new DeleteIngredientRequest(data);

        mvc.perform(delete("/groups/2/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(404))
                .andDo(print());
    }

    @Test
    void 식재료_수량_갱신_204() throws Exception {
        List<UpdateIngredientCountDataRequest> data = new ArrayList<>();
        data.add(new UpdateIngredientCountDataRequest(1,"2g"));
        data.add(new UpdateIngredientCountDataRequest(2,"0개"));

        UpdateIngredientCountRequest request = new UpdateIngredientCountRequest(data);

        mvc.perform(put("/groups/1/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(204))
                .andDo(print());

    }
    @Test
    void 식재료_수량_갱신_400_request_형식() throws Exception {
        List<UpdateIngredientCountDataRequest> data = new ArrayList<>();
        data.add(new UpdateIngredientCountDataRequest(1,"2g"));
        data.add(new UpdateIngredientCountDataRequest(2,""));

        UpdateIngredientCountRequest request = new UpdateIngredientCountRequest(data);

        mvc.perform(put("/groups/1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(204))
                .andDo(print());

    }

    @Test
    void 식재료_수량_갱신_404_그룹아이디_없음() throws Exception {
        List<UpdateIngredientCountDataRequest> data = new ArrayList<>();
        data.add(new UpdateIngredientCountDataRequest(1,"2g"));
        data.add(new UpdateIngredientCountDataRequest(2,"0개"));

        UpdateIngredientCountRequest request = new UpdateIngredientCountRequest(data);

        mvc.perform(put("/groups/122222/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(404))
                .andDo(print());

    }
    @Test
    void 식재료_수량_갱신_404_그룹안에_식재료없음() throws Exception {
        List<UpdateIngredientCountDataRequest> data = new ArrayList<>();
        data.add(new UpdateIngredientCountDataRequest(1,"1개"));
        data.add(new UpdateIngredientCountDataRequest(2,"0개"));

        UpdateIngredientCountRequest request = new UpdateIngredientCountRequest(data);

        mvc.perform(put("/groups/2/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().is(404))
                .andDo(print());
    }
}