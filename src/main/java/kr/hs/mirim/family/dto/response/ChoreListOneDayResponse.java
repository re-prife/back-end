package kr.hs.mirim.family.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChoreListOneDayResponse {
    private List<ChoreListOneDayDataResponse> data;

    public static ChoreListOneDayResponse of(List<ChoreListOneDayDataResponse> data){
        return ChoreListOneDayResponse.builder()
                .data(data)
                .build();
    }
}
