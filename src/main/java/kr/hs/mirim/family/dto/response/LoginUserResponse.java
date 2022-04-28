package kr.hs.mirim.family.dto.response;

import kr.hs.mirim.family.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class LoginUserResponse {
    @NonNull
    private Long userId;

    @NonNull
    private String userName;

    @NonNull
    private String userNickname;

    @NonNull
    private String userEmail;

    @NonNull
    private String userImageName;

    public LoginUserResponse(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userNickname = user.getUserNickname();
        this.userEmail = user.getUserEmail();
        this.userImageName = user.getUserImageName();
    }
}
