package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Schema(description = "그룹 공지사항 조회 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupReportResponse {
    @Schema(description = "그룹 아이디", example = "2")
    @NonNull
    private String groupReport;
}
