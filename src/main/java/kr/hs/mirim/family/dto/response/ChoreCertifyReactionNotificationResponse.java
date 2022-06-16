package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hs.mirim.family.entity.chore.ChoreCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Schema(description = "집안일 인증 알림 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChoreCertifyReactionNotificationResponse {
    @Schema(description = "집안일 제목", example = "요리 중입니다.")
    @NonNull
    private String choreTitle;

    @Schema(description = "집안일 카테고리", example = "COOK")
    @NonNull
    private ChoreCategory choreCategory;
}
