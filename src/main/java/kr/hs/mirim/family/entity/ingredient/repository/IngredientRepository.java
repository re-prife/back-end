package kr.hs.mirim.family.entity.ingredient.repository;

import kr.hs.mirim.family.entity.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, IngredientRepositoryExtension {
}
