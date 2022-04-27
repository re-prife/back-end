package kr.hs.mirim.family.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateGroupRequestDto {
    private long userId;
    private String groupName;
}
