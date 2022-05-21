package kr.hs.mirim.family.entity.quest.repository;

import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.quest.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findAllByGroup(Group group);
}
