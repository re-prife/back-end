package kr.hs.mirim.family.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class JoinGroupRequest {
    @NotNull
    private long userId;
    @NotEmpty
    private String groupInviteCode;
}
