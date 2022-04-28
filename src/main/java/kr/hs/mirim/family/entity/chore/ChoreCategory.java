package kr.hs.mirim.family.entity.chore;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChoreCategory {
    DISH_WASHING("설거지"),
    SHOPPING("장보기"),
    COOK("요리");

    private String category;
}
