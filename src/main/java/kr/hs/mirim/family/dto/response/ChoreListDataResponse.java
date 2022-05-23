package kr.hs.mirim.family.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import kr.hs.mirim.family.entity.chore.ChoreCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChoreListDataResponse {
    @NotNull
    private Long choreId;

    @NotNull
    private Long userId;

    @NotNull
    private String choreTitle;

    @NotNull
    private ChoreCategory choreCategory;

    @NotNull
    private LocalDate choreDate;
}
