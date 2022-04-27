package kr.hs.mirim.family.dto.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpDto {
    @NotNull
    private String userName;

    @NotNull
    private String userEmail;

    @NotNull
    private String userPassword;
}
