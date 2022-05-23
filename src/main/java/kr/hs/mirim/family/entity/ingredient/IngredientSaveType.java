package kr.hs.mirim.family.entity.ingredient;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IngredientSaveType {
    FRIDGE("냉장"),
    FREEZER("냉동"),
    ROOM_TEMP("실온");

    private String saveType;

    @JsonCreator
    public static IngredientSaveType fromValue(String value){
        switch (value){
            case "냉장" :
                return IngredientSaveType.FRIDGE;
            case "냉동" :
                return IngredientSaveType.FREEZER;
            case "실온" :
                return IngredientSaveType.ROOM_TEMP;
        }
        return null;
    }
}
