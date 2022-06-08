package kr.hs.mirim.family.entity.chore.repository;

import kr.hs.mirim.family.dto.response.ChoreKingResponse;
import kr.hs.mirim.family.dto.response.ChoreListResponse;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface ChoreRepositoryExtension {
    List<ChoreListResponse> findByChoreGroup_GroupIdAndDateMonth(Long groupId, YearMonth date);
    List<ChoreListResponse> findByChoreGroup_GroupIdAndDate(Long groupId, LocalDate date);
  
    List<ChoreKingResponse> monthKing(long groupId, YearMonth date);
}