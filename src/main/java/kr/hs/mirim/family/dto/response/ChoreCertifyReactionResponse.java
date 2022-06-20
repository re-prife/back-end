package kr.hs.mirim.family.dto.response;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChoreCertifyReactionResponse {
    @Schema(description = "집안일 당번 ID", example = "1")
    @NotNull
    private Long requesterId;
}
