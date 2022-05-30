package kr.hs.mirim.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Schema(description = "그룹의 공지사항 갱신 Request")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {
    @Schema(description = "갱신할 공지사항 내용", example = "이번주 청소 담당 확인")
    @NotEmpty
    @Length(max=50)
    private String groupReport;
}
