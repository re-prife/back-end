package kr.hs.mirim.family.entity.chore.repository;

import kr.hs.mirim.family.dto.response.KingResponse;

import java.time.YearMonth;
import java.util.List;

public interface ChoreRepositoryExtension {
    List<KingResponse> monthKing(long groupId, YearMonth date);
}
