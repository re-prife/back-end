package kr.hs.mirim.family.entity.user.repository;

import kr.hs.mirim.family.entity.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
