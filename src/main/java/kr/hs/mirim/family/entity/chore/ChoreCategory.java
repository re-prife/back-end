package kr.hs.mirim.family.entity.chore;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.hs.mirim.family.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum ChoreCategory {
    DISH_WASHING("설거지"),
    SHOPPING("장보기"),
    COOK("요리");

    private String category;

    @JsonCreator
    public static ChoreCategory fromString(String category){
        try {
            return ChoreCategory.valueOf(category);
        } catch (Exception e) {
            throw new DataNotFoundException("존재하지 않는 목록의 형식입니다.");
        }
    }

}
