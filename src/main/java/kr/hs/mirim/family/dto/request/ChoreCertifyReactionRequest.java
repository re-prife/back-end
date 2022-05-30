package kr.hs.mirim.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Schema(description = "집안일 인증 요청 응답 Request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChoreCertifyReactionRequest {
    @Schema(description = "인증 요청에 대한 응답", example = "SUCCESS", allowableValues = {"SUCCESS", "FAIL"})
    @NotEmpty
    String reaction;
}
