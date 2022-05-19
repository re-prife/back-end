package kr.hs.mirim.family.dto.response;

import kr.hs.mirim.family.entity.quest.Quest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestListResponse {
    @NonNull
    private long requestUserId;
    @NonNull
    private String questTitle;
    @NonNull
    private String questContent;
    @NonNull
    private LocalDateTime questCreateDate;
    @NonNull
    private LocalDateTime questModifiedDate;
    @NonNull
    private boolean completeCheck;
    @NonNull
    private long acceptUserId;

    public static QuestListResponse of(Quest quest) {
        QuestListResponse questListResponse = new QuestListResponse();
        questListResponse.requestUserId = quest.getUser().getUserId();
        questListResponse.questTitle = quest.getQuestTitle();
        questListResponse.questContent = quest.getQuestContent();
        questListResponse.questCreateDate = quest.getCreatedDate();
        questListResponse.questModifiedDate = quest.getModifiedDate();
        questListResponse.completeCheck = quest.isCompleteCheck();
        questListResponse.acceptUserId = quest.getAcceptUserId();
        return questListResponse;

    }
}
