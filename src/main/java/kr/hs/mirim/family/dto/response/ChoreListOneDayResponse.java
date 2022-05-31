package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "집안일 하루 조회 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChoreListOneDayResponse {
    private List<ChoreListDataResponse> data;
}
