package kr.hs.mirim.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteIngredientDataRequest {

    @Schema(description = "삭제할 식재료 ID", example = "1")
    @NotNull
    private long ingredientId;
}
