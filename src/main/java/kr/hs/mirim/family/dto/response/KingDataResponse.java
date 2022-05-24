package kr.hs.mirim.family.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

@Getter
@Builder
@AllArgsConstructor
public class KingDataResponse {
    private final List<KingResponse> data;
}