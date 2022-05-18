package kr.hs.mirim.family.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class LoginUserRequest {
    @NotEmpty
    @Email
    private String userEmail;

    @NotEmpty
    private String userPassword;
}
