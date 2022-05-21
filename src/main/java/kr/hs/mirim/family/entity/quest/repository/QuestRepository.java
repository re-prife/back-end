package kr.hs.mirim.family.entity.quest.repository;

import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.quest.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findAllByGroup(Group group);
    @Modifying
    @Transactional
    @Query(value = "UPDATE quest_tb q set q.accept_user_id = :userId where q.quest_id = :questId", nativeQuery = true)
    void updateAcceptUserId(long questId, long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE quest_tb q set q.complete_check = true where q.quest_id = :questId", nativeQuery = true)
    void updateCompleteCheck(long questId);

    boolean existsByQuestIdAndGroup(long id, Group group);
}
