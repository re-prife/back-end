package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hs.mirim.family.entity.chore.ChoreCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "이달의 집안일 왕 Request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserChoreKingResponse {

    @Schema(description = "집안일 카테고리", example = "COOK")
    @NotEmpty
    private ChoreCategory category;

    @Schema(description = "집안일 왕 ID", example = "4")
    @NotNull
    private long userId;

    @Schema(description = "집안일 완료 횟수", example = "12")
    @NotNull
    private long count;
}
