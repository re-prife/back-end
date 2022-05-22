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
    @JsonProperty(value = "chore_id")
    private Long choreId;

    @NotNull
    @JsonProperty(value = "user_id")
    private Long userId;

    @NotNull
    @JsonProperty(value = "chore_title")
    private String choreTitle;

    @NotNull
    @JsonProperty(value = "chore_category")
    private ChoreCategory choreCategory;

    @NotNull
    private LocalDate choreDate;
}
