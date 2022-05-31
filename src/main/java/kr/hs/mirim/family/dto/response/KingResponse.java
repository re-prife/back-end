package kr.hs.mirim.family.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Schema(description = "이달의 왕 조회 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KingResponse {

    private List<ChoreKingResponse> choreKing;
    private QuestKingResponse questKing;
}
