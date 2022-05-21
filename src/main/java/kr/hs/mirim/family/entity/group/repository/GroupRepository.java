package kr.hs.mirim.family.entity.group.repository;

import kr.hs.mirim.family.entity.group.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Long>, GroupRepositoryExtension {
    boolean existsByGroupInviteCode(String code);

    Optional<Group> findByGroupInviteCode(String code);
    Optional<Group> findByGroupId(long groupId);
}
