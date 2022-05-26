package kr.hs.mirim.family.dto.response;

import kr.hs.mirim.family.entity.chore.ChoreCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChoreKingResponse {

    @NotEmpty
    private ChoreCategory category;

    @NotNull
    private long userId;

    @NotNull
    private long count;
}
