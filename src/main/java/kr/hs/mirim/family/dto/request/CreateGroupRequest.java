package kr.hs.mirim.family.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class CreateGroupRequest {
    @NotEmpty
    private String groupName;
}
