package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "이달의 심부름 왕 Request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MonthQuestKingResponse {

    @Schema(description = "심부름 왕 ID", example = "2")
    @NotNull
    private long userId;

    @Schema(description = "유저 닉네임", example = "늘하")
    @NotEmpty
    private String userNickname;

    @Schema(description = "유저 이미지 경로", example = "/upload/user_4.png")
    @NotEmpty
    private String userImagePath;

    @Schema(description = "심부름 완료 횟수", example = "8")
    @NotNull
    private long count;
}
