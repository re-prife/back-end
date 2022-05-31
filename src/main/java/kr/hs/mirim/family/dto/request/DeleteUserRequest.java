package kr.hs.mirim.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Schema(description = "회원 삭제 Request")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserRequest {
    @Schema(description = "회원 비밀번호", example = "doremisol")
    @NotNull
    private String userPassword;
}
