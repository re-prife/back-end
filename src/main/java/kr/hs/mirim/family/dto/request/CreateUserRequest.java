package kr.hs.mirim.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Schema(description = "회원 생성 Request")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @Schema(description = "회원 이름", example = "Min J")
    @NotEmpty
    private String userName;

    @Schema(description = "회원 닉네임", example = "취준생")
    @NotEmpty
    private String userNickname;

    @Schema(description = "회원 이메일", example = "m04j00@gmail.com")
    @NotEmpty
    @Email
    private String userEmail;

    @Schema(description = "회원 비밀번호", example = "doremisol")
    @NotEmpty
    @Size(min=8)
    private String userPassword;
}
