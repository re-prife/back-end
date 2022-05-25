package kr.hs.mirim.family.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @NotEmpty
    private String userName;

    @NotEmpty
    private String userNickname;

    @NotEmpty
    private String userPassword;

    @NotNull
    private String userImageName;
}
