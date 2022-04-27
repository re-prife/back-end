package kr.hs.mirim.family.entity.user.repository;

import kr.hs.mirim.family.entity.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_tb u set u.group_id = :groupId where u.user_id = :userId", nativeQuery = true)
    void updateGroupId(long groupId, long userId);
}
