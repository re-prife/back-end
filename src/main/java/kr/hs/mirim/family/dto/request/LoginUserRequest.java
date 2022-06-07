package kr.hs.mirim.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Schema(description = "회원 로그인 Request")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest {
    @Schema(description = "회원 이메일", example = "m04j00@gmail.com")
    @NotEmpty
    @Email
    private String userEmail;

    @Schema(description = "회원 비밀번호", example = "doremisol")
    @NotEmpty
    private String userPassword;
}
