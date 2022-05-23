package kr.hs.mirim.family.entity.chore.repository;

import kr.hs.mirim.family.dto.response.ChoreListOneDayDataResponse;

import java.time.LocalDate;
import java.util.List;

public interface ChoreRepositoryExtension {
    List<ChoreListOneDayDataResponse> findByChoreGroup_GroupIdAndDate(Long groupId, LocalDate date);
}
