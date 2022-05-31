package kr.hs.mirim.family.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "집안일 생성 Request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChoreRequest {
    @Schema(description = "집안일 제목", example = "하늘이가 요리하는 날")
    @NotEmpty
    private String choreTitle;

    @Schema(description = "집안일 카테고리", example = "COOK", allowableValues = {"DISH_WASHING", "SHOPPING", "COOK"})
    @NotNull
    private String choreCategory;

    @Schema(description = "집안일을 하는 날짜 (년-월-일)", example = "2022-05-14")
    @NotNull
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate choreDate;

    @Schema(description = "집안일 담당자 ID", example = "4")
    @NotNull
    private long choreUserId;
}
