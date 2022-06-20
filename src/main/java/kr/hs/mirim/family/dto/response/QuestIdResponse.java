package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "심부름 생성 Response")
public class QuestIdResponse {
    @NonNull
    @Schema(description = "심부름 ID", example = "1")
    private Long questId;
}
