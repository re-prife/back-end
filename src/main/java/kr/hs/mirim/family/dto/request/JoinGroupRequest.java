package kr.hs.mirim.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Schema(description = "그룹 가입 Request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinGroupRequest {
    @Schema(description = "가입할 그룹의 초대 코드", example = "mon0514")
    @NotEmpty
    private String groupInviteCode;
}
