package kr.hs.mirim.family.entity.quest.repository;

import kr.hs.mirim.family.dto.response.QuestKingResponse;

import java.time.YearMonth;

public interface QuestRepositoryExtension{
    QuestKingResponse questKingMonth(long groupId, YearMonth date);
}
