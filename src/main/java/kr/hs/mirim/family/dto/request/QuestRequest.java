package kr.hs.mirim.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "심부름 생성 Request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestRequest {
    @Schema(description = "심부름 제목", example = "저녁에 치킨 사 올 사람")
    @NotEmpty
    private String questTitle;

    @Schema(description = "심부름 내용", example = "누가 치킨 사왔으면 좋겠다... ^_^")
    @NotNull
    private String questContent;
}
