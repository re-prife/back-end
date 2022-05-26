package kr.hs.mirim.family.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPasswordRequest {
    @NotNull
    private String userPassword;

    @NotEmpty
    private String userNewPassword;

    @NotNull
    private String userNewPasswordCheck;
}
