package kr.hs.mirim.family.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Schema(description = "그룹 생성 Request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupRequest {
    @Schema(description = "생성할 그룹의 이름", example = "민지네")
    @NotEmpty
    private String groupName;
}
