package kr.hs.mirim.family.dto.response;

import kr.hs.mirim.family.entity.quest.Quest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestResponse {
    @NonNull
    private long questId;
    @NonNull
    private long requestUserId;
    @NonNull
    private String questTitle;
    @NonNull
    private boolean completeCheck;
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
