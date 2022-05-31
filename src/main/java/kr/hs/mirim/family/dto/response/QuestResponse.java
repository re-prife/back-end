package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hs.mirim.family.entity.quest.Quest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Schema(description = "심부름 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestResponse {
    @Schema(description = "심부름 ID", example = "1")
    @NonNull
    private long questId;

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

    public static QuestResponse of(Quest quest) {
        QuestResponse questListResponse = new QuestResponse();
        questListResponse.questId = quest.getQuestId();
        questListResponse.requestUserId = quest.getUser().getUserId();
        questListResponse.questTitle = quest.getQuestTitle();
        questListResponse.completeCheck = quest.isCompleteCheck();
        questListResponse.acceptUserId = quest.getAcceptUserId();
        return questListResponse;
    }
}
