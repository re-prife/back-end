package kr.hs.mirim.family.entity.chore.repository;

import kr.hs.mirim.family.dto.response.ChoreListDataResponse;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface ChoreRepositoryExtension {
    List<ChoreListDataResponse> findByChoreGroup_GroupIdAndDateMonth(Long groupId, YearMonth date);
    List<ChoreListDataResponse> findByChoreGroup_GroupIdAndDate(Long groupId, LocalDate date);
  
    List<KingResponse> monthKing(long groupId, YearMonth date);
}