package kr.hs.mirim.family.entity.ingredient.repository;

import kr.hs.mirim.family.dto.response.IngredientListResponse;

import java.util.List;

public interface IngredientRepositoryExtension {
    List<IngredientListResponse> ingredientSaveTypeList(long groupId, String saveType);
    void ingredientCountUpdate(long groupId, long ingredientId, String ingredientCount);
}
