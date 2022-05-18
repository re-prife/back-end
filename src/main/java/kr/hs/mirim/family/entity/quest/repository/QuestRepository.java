package kr.hs.mirim.family.entity.quest.repository;

import kr.hs.mirim.family.entity.quest.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<Quest, Long> {
}
