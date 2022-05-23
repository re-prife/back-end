package kr.hs.mirim.family.entity.ingredient;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

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

    @JsonCreator
    public static IngredientCategory fromValue(String value){
        switch (value){
            case "채소" :
                return IngredientCategory.VEGGIE;
            case "과일" :
                return IngredientCategory.FRUIT;
            case "수산물" :
                return IngredientCategory.SEA_FOOD;
            case "곡물" :
                return IngredientCategory.GRAIN;
            case "육류" :
                return IngredientCategory.MEAT;
            case "양념" :
                return IngredientCategory.SEASONING;
            case "음료" :
                return IngredientCategory.BEVERAGE;
            case "가공식품" :
                return IngredientCategory.PROCESSED_FOOD;
            case "간식" :
                return IngredientCategory.SNACK;
            case "유제품" :
                return IngredientCategory.DAIRY_PRODUCT;
            case "반찬" :
                return IngredientCategory.SIDE_DISH;
            case "기타" :
                return IngredientCategory.ETC;
        }
        return null;
    }
}
