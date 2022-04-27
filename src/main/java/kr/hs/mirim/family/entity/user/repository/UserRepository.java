package kr.hs.mirim.family.entity.user.repository;

import kr.hs.mirim.family.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserEmail(String email);
}
