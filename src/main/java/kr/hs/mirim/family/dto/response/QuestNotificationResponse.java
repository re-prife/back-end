package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Schema(description = "심부름 생성 알림 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestNotificationResponse {
    @Schema(description = "심부름 요청자 닉네임", example = "비지피웍스")
    @NonNull
    private String userNickname;

    @Schema(description = "심부름 제목", example = "치킨 사 올 사람")
    @NonNull
    private String questTitle;
}
