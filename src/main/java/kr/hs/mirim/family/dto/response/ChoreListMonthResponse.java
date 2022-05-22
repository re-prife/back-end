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
public class ChoreListMonthResponse {
    private List<ChoreListDataResponse> data;

    public static ChoreListMonthResponse of(List<ChoreListDataResponse> data){
        return ChoreListMonthResponse.builder()
                .data(data)
                .build();
    }
}
