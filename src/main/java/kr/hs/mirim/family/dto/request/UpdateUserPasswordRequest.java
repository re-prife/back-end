package kr.hs.mirim.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "회원 갱신 Request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPasswordRequest {
    @Schema(description = "기존 비밀번호", example = "doremisol")
    @NotNull
    private String userPassword;

    @Schema(description = "변경할 비밀번호", example = "doremifasol")
    @NotEmpty
    private String userNewPassword;

    @Schema(description = "변경할 비밀번호 확인", example = "doremifasol")
    @NotNull
    private String userNewPasswordCheck;
}
