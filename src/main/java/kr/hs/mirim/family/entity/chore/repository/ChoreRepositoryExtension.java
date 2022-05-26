package kr.hs.mirim.family.entity.chore.repository;

import kr.hs.mirim.family.dto.response.ChoreKingResponse;
import kr.hs.mirim.family.dto.response.ChoreListDataResponse;
import kr.hs.mirim.family.dto.response.KingResponse;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface ChoreRepositoryExtension {
    List<ChoreListDataResponse> findByChoreGroup_GroupIdAndDateMonth(Long groupId, YearMonth date);
    List<ChoreListDataResponse> findByChoreGroup_GroupIdAndDate(Long groupId, LocalDate date);
  
    List<ChoreKingResponse> monthKing(long groupId, YearMonth date);
}