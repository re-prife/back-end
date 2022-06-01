package kr.hs.mirim.family.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "심부름 상세 조회 Response")
public class QuestFindOneResponse {

    @Schema(description = "심부름 요청자 ID", example = "1")
    @NonNull
    private long requestUserId;

    @Schema(description = "심부름 제목", example = "저녁에 치킨 사 올 사람")
    @NonNull
    private String questTitle;

    @Schema(description = "심부름 완료 여부", example = "False")
    @NonNull
    private boolean completeCheck;

    @Schema(description = "심부름 수락자 ID", example = "3")
    @NonNull
    private long acceptUserId;

    @Schema(description = "심부름 생성한 날짜", example = "2022-05-31")
    @JsonFormat(timezone = "Asia/Seoul", pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @NotNull
    private LocalDateTime questCreatedDate;

}
