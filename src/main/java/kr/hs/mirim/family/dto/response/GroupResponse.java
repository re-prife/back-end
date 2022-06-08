package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "그룹 생성 / 가입 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {
    @Schema(description = "그룹 아이디", example = "1")
    @NonNull
    private long groupId;
    @Schema(description = "그룹 초대 코드", example = "mon0514")
    @NonNull
    private String groupInviteCode;
}
