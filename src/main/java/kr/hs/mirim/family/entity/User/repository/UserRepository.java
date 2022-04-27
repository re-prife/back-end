package kr.hs.mirim.family.entity.User.repository;

import kr.hs.mirim.family.entity.User.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
