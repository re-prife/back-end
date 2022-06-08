package kr.hs.mirim.family.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.hs.mirim.family.entity.ingredient.IngredientCategory;
import kr.hs.mirim.family.entity.ingredient.IngredientSaveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Schema(description = "식재료 조회 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientListResponse {

    @Schema(description = "식재료 Id", example = "1")
    @NonNull
    private long ingredientId;

    @Schema(description = "식재료 이름", example = "햇감자")
    @NotEmpty
    private String ingredientName;

    @Schema(description = "식재료 저장 방법", example = "FRIDGE")
    @NonNull
    private IngredientSaveType ingredientSaveType;

    @Schema(description = "식재료 유통기한", example = "2023-03-01")
    @NonNull
    @JsonFormat(timezone = "Asia/Seoul", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate ingredientExpirationDate;

    @Schema(description = "식재료 구매날짜", example = "2022-03-01")
    @NonNull
    @JsonFormat(timezone = "Asia/Seoul", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate ingredientPurchaseDate;

    @Schema(description = "식재료 카테고리", example = "VEGGIE")
    @NonNull
    private IngredientCategory ingredientCategory;

    @Schema(description = "식재료 수량", example = "3개")
    @NotEmpty
    private String ingredientCount;

    @Schema(description = "식재료 이미지 주소", example = "/upload/ingredient_1.png")
    @NotEmpty
    private String ingredientImagePath;

    @Schema(description = "식재료 메모", example = "맛있는 수박박박")
    @NotEmpty
    private String ingredientMemo;

    @Schema(description = "식재료 유통기한을 알려주는 색", example = "RED")
    @NonNull
    private String ingredientColor;

    public void setIngredientColor(String ingredientColor) {
        this.ingredientColor = ingredientColor;
    }
}
