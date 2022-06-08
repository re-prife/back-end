package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "회원 로그인 Response")
@Getter
@Builder
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

    @Schema(description = "회원 이미지 주소", example = "/upload/user_1.png")
    @NonNull
    private String userImagePath;

    @Schema(description = "그룹 ID", example = "1")
    private Long groupId;
}
