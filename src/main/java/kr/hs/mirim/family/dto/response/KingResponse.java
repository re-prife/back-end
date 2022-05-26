package kr.hs.mirim.family.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KingResponse {

    private List<ChoreKingResponse> choreKing;
    private List<QuestKingResponse> questKing;
}
