package kr.hs.mirim.family.dto.response;


import kr.hs.mirim.family.entity.chore.ChoreCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KingResponse {
    @NotEmpty
    private ChoreCategory category;

    @NotNull
    private long userId;

    @NotNull
    private Long questCount;
}
