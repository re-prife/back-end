package kr.hs.mirim.family.dto.response;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.hs.mirim.family.entity.chore.ChoreCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChoreListDataResponse {
    @Schema(description = "집안일 ID", example = "1")
    @NotNull
    private Long choreId;

    @Schema(description = "집안일 담당자 ID", example = "4")
    @NotNull
    private Long userId;

    @Schema(description = "집안일 제목", example = "하늘이 요리하는 날")
    @NotNull
    private String choreTitle;

    @Schema(description = "집안일 카테고리", example = "COOK")
    @NotNull
    private ChoreCategory choreCategory;

    @Schema(description = "집안일을 하는 날짜", example = "2022-05-14")
    @NotNull
    private LocalDate choreDate;
}
