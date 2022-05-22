package kr.hs.mirim.family.entity.chore.repository;

import kr.hs.mirim.family.dto.response.ChoreListDataResponse;

import java.time.LocalDate;
import java.util.List;

public interface ChoreRepositoryExtension {
    List<ChoreListDataResponse> findByChoreGroupAndDate(Long groupId, LocalDate date);
    List<ChoreListDataResponse> findByChoreGroupAndDateMonth(Long groupId);
}
