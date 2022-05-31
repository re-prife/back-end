package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원 상세 정보 조회 Request")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFindResponse {
    @Schema(description = "회원 이름", example = "Min J")
    private String userName;

    @Schema(description = "회원 닉네임", example = "취준생")
    private String userNickname;

    @Schema(description = "회원 이메일", example = "m04j00@gmail.com")
    private String userEmail;

    @Schema(description = "회원 이미지 주소", example = "42c64cf214a")
    private String userImageName;

    @Schema(description = "이전 달의 왕")
    private UserFindKingResponse king;
}