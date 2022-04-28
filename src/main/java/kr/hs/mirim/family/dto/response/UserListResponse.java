package kr.hs.mirim.family.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse {
    @NonNull
    private Long userId;
    @NonNull
    private String userName;
    @NonNull
    private String userNickname;
    @NonNull
    private String userImageName;
}
