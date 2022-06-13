package kr.hs.mirim.family.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.hs.mirim.family.entity.chore.ChoreCategory;
import kr.hs.mirim.family.entity.chore.ChoreCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Schema(description = "집안일 인증 알림 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChoreNotificationResponse {
    @Schema(description = "집안일 제목", example = "요리 중입니다.")
    @NonNull
    private String choreTitle;

    @Schema(description = "집안일 카테고리", example = "COOK")
    @NonNull
    private ChoreCategory choreCategory;

    @Schema(description = "집안일 상태", example = "BEFORE")
    @NonNull
    private ChoreCheck choreCheck;

    @Schema(description = "집안일 담당자 별명", example = "비지피웍스")
    @NonNull
    private String userNickname;

    @Schema(description = "집안일 날짜", example = "2022-06-13")
    @NonNull
    @JsonFormat(timezone = "Asia/Seoul", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate choreDate;

    @Schema(description = "등록날짜", example = "2022-06-13")
    @NonNull
    @JsonFormat(timezone = "Asia/Seoul", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @Schema(description = "수정날짜", example = "2022-06-13")
    @NonNull
    @JsonFormat(timezone = "Asia/Seoul", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;
}
