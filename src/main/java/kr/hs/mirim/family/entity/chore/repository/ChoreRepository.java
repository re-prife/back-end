import kr.hs.mirim.family.entity.chore.ChoreCategory;
import kr.hs.mirim.family.entity.chore.ChoreCheck;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface ChoreRepository extends CrudRepository<Chore, Long>, ChoreRepositoryExtension {
    boolean existsByChoreDateAndChoreCategoryAndUser_UserId(LocalDate date, ChoreCategory choreCategory, Long userId);
  
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Chore c SET c.choreCheck = :check WHERE c.choreId = :choreId")
    void updateChoreCheck(Long choreId, ChoreCheck check);
}
