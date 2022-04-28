package kr.hs.mirim.family.entity.ingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IngredientSaveType {
    FRIDGE("냉장"),
    FREEZER("냉동"),
    ROOM_TEMP("실온");

    private String saveType;
}
