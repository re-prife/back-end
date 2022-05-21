package kr.hs.mirim.family.dto.response;

import kr.hs.mirim.family.entity.ingredient.IngredientCategory;
import kr.hs.mirim.family.entity.ingredient.IngredientSaveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientListResponse {

    @NotNull
    private String ingredientName;

    @NotNull
    private IngredientSaveType ingredientSaveType;

    @NotNull
    private LocalDate ingredientExpirationDate;

    @NotNull
    private IngredientCategory ingredientCategory;

    @NotNull
    private String ingredientCount;

    @NotNull
    private String ingredientImageName;
}
