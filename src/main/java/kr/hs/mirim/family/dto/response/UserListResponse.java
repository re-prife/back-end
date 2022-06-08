package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Schema(description = "회원 목록 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse {
    @Schema(description = "회원 ID", example = "1")
    @NonNull
    private Long userId;
    @Schema(description = "회원 이름", example = "Min J")
    @NonNull
    private String userName;
    @Schema(description = "회원 닉네임", example = "취준생")
    @NonNull
    private String userNickname;
    @Schema(description = "회원 이미지 주소", example = "/upload/user_1.png")
    @NonNull
    private String userImagePath;
}
