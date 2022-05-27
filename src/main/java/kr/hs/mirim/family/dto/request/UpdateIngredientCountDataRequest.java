package kr.hs.mirim.family.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIngredientCountDataRequest {

    @NotNull
    private long ingredientId;

    @NotNull
    private String ingredientCount;
}