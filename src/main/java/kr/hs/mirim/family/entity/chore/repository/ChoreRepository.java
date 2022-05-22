package kr.hs.mirim.family.entity.chore.repository;

import kr.hs.mirim.family.entity.chore.Chore;
import kr.hs.mirim.family.entity.chore.ChoreCheck;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChoreRepository extends CrudRepository<Chore, Long>, ChoreRepositoryExtension {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Chore c SET c.choreCheck = :check WHERE c.choreId = :choreId")
    void updateChoreCheck(Long choreId, ChoreCheck check);
}
