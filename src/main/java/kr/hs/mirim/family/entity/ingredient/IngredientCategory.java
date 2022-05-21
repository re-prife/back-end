package kr.hs.mirim.family.entity.ingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum IngredientCategory {

    VEGGIE("채소"),
    FRUIT("과일"),
    SEA_FOOD("수산물"),
    GRAIN("곡물"),
    MEAT("육류"),
    SEASONING("양념"),
    BEVERAGE("음료"),
    PROCESSED_FOOD("가공식품"),
    SNACK("간식"),
    DAIRY_PRODUCT("유제품"),
    SIDE_DISH("반찬"),
    ETC("기타");

    private String category;

}
