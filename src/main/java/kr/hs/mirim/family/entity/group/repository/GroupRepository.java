package kr.hs.mirim.family.entity.group.repository;

import kr.hs.mirim.family.entity.group.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {
    boolean existsByGroupInviteCode(String code);
}
