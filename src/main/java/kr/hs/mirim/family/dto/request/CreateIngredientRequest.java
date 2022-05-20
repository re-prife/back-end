package kr.hs.mirim.family.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.hs.mirim.family.entity.ingredient.IngredientCategory;
import kr.hs.mirim.family.entity.ingredient.IngredientSaveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateIngredientRequest {

    @NotEmpty
    private String ingredientName;

    @NotNull
    private IngredientSaveType ingredientSaveType;

    @NotNull
    private IngredientCategory ingredientCategory;

    @NotNull
    private int ingredientCount;

    @NotNull
    private String ingredientMemo;

    @NotNull
    @JsonFormat(timezone = "Asia/Seoul", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate ingredientPurchaseDate;

    @NotNull
    @JsonFormat(timezone = "Asia/Seoul", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate ingredientExpirationDate;

}
