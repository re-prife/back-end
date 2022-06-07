package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.hs.mirim.family.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Schema(description = "회원 로그인 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserResponse {
    @Schema(description = "회원 ID", example = "1")
    @NonNull
    private Long userId;

    @Schema(description = "회원 이름", example = "Min J")
    @NonNull
    private String userName;

    @Schema(description = "회원 닉네임", example = "취준생")
    @NonNull
    private String userNickname;

    @Schema(description = "회원 이메일", example = "m04j00@gmail.com")
    @NonNull
    private String userEmail;

    @Schema(description = "회원 이미지 주소", example = "76se4ccedf14")
    @NonNull
    private String userImageName;

    @Schema(description = "그룹 ID", example = "1")
    private Long groupId;

    public LoginUserResponse(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userNickname = user.getUserNickname();
        this.userEmail = user.getUserEmail();
        this.userImageName = user.getUserImageName();
        this.groupId = user.getGroup().getGroupId();
    }
}
