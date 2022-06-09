package kr.hs.mirim.family.entity.chore.repository;

import kr.hs.mirim.family.dto.response.MonthChoreKingResponse;
import kr.hs.mirim.family.dto.response.UserChoreKingResponse;
import kr.hs.mirim.family.dto.response.ChoreListResponse;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface ChoreRepositoryExtension {
    List<ChoreListResponse> findByChoreGroup_GroupIdAndDateMonth(Long groupId, YearMonth date);
    List<ChoreListResponse> findByChoreGroup_GroupIdAndDate(Long groupId, LocalDate date);
  
    List<UserChoreKingResponse> userChoreKing(long groupId, YearMonth date);
    List<MonthChoreKingResponse> monthChoreKing(long groupId, YearMonth date);
}