package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Schema(description = "식재료 아이디를 반환하는 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientIdResponse {
    @Schema(description = "식재료 아이디", example = "1")
    @NonNull
    private long ingredientId;
}
