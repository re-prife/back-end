package kr.hs.mirim.family.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFindResponse {
    private String userName;
    private String userNickname;
    private String userEmail;
    private String userImageName;
    private UserFindKingResponse king;
}