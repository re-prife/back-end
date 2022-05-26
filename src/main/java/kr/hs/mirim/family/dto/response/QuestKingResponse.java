package kr.hs.mirim.family.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestKingResponse {
    @NotNull
    private long userId;

    @NotNull
    private Long questCount;
}
