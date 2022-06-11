package kr.hs.mirim.family.entity.quest.repository;

import kr.hs.mirim.family.dto.response.MonthQuestKingResponse;
import kr.hs.mirim.family.dto.response.UserQuestKingResponse;

import java.time.YearMonth;

public interface QuestRepositoryExtension{
    UserQuestKingResponse userQuestKing(long groupId, YearMonth date);
    MonthQuestKingResponse monthQuestKing(long groupId, YearMonth date);
}
