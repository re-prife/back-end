package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Schema(description = "그룹 생성 / 가입 Response")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupIdResponse {
    @Schema(description = "그룹 아이디", example = "1")
    @NonNull
    private long groupId;
}
