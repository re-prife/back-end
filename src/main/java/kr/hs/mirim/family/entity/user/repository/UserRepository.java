package kr.hs.mirim.family.entity.user.repository;

import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryExtension {

    boolean existsByUserEmail(String email);

    boolean existsByUserIdAndGroup(long id, Group group);

    Optional<User> findByUserEmail(String email);
    Optional<User> findById(long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_tb u set u.group_id = :groupId where u.user_id = :userId", nativeQuery = true)
    void updateGroupId(long groupId, long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_tb u set u.user_image_path = :userImagePath where u.user_id = :userId", nativeQuery = true)
    void updateUserImage(long userId, String userImagePath);

    @Query(value = "SELECT userId FROM User where group = :group")
    List<Long> findAllByGroup(Optional<Group> group);
}
