package kr.hs.mirim.family.entity.chore.repository;

import kr.hs.mirim.family.entity.chore.Chore;
import kr.hs.mirim.family.entity.chore.ChoreCategory;
import kr.hs.mirim.family.entity.chore.ChoreCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ChoreRepository extends JpaRepository<Chore, Long>, ChoreRepositoryExtension {
    boolean existsByChoreDateAndChoreCategoryAndUser_UserId(LocalDate date, ChoreCategory choreCategory, Long userId);
  
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Chore c SET c.choreCheck = :check WHERE c.choreId = :choreId")
    void updateChoreCheck(Long choreId, ChoreCheck check);
}
