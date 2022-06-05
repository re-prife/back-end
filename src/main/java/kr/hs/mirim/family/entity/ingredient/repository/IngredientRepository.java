package kr.hs.mirim.family.entity.ingredient.repository;

import kr.hs.mirim.family.entity.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, IngredientRepositoryExtension {
    @Modifying
    @Transactional
    @Query(value = "UPDATE ingredient_tb i set i.ingredient_image_path = :ingredientImagePath where i.ingredient_id = :ingredientId", nativeQuery = true)
    void updateIngredientImage(long ingredientId, String ingredientImagePath);
}
