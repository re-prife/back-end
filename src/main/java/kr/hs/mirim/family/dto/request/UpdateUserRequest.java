package kr.hs.mirim.family.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Schema(description = "회원 정보 갱신 Request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @Schema(description = "회원 이름", example = "Min J")
    @NotEmpty
    private String userName;

    @Schema(description = "회원 닉네임", example = "직딩")
    @NotEmpty
    private String userNickname;

}
