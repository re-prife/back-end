package kr.hs.mirim.family.entity.user.repository;

import kr.hs.mirim.family.dto.response.UserListResponse;

import java.util.List;

public interface UserRepositoryExtension {
    List<UserListResponse> userList(Long groupId, Long userId);
}
