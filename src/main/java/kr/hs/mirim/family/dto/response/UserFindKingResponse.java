package kr.hs.mirim.family.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "회원 왕 정보 Response")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFindKingResponse {
    @Schema(description = "집안일 왕")
    private List<UserChoreKingResponse> choreKing;

    @Schema(description = "심부름 왕")
    private UserQuestKingResponse questKing;
}