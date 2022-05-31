package kr.hs.mirim.family.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.hs.mirim.family.entity.ingredient.IngredientCategory;
import kr.hs.mirim.family.entity.ingredient.IngredientSaveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "식재료 Request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRequest {

    @Schema(description = "식재료 이름", example = "햇감자")
    @NotEmpty
    private String ingredientName;

    @Schema(description = "식재료 저장 방법", example = "FRIDGE")
    @NotNull
    private IngredientSaveType ingredientSaveType;

    @Schema(description = "식재료 카테고리", example = "VEGGIE")
    @NotNull
    private IngredientCategory ingredientCategory;

    @Schema(description = "식재료 수량", example = "3개")
    @NotEmpty
    private String ingredientCount;

    @Schema(description = "식재료 메모", example = "햇감자 구워먹기")
    @NotNull
    private String ingredientMemo;

    @Schema(description = "식재료 구입 날짜", example = "2022-05-14")
    @NotNull
    @JsonFormat(timezone = "Asia/Seoul", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate ingredientPurchaseDate;

    @Schema(description = "식재료 유통기한", example = "2023-03-01")
    @NotNull
    @JsonFormat(timezone = "Asia/Seoul", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate ingredientExpirationDate;
}
