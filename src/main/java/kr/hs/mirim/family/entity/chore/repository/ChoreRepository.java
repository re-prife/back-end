package kr.hs.mirim.family.entity.chore.repository;

import kr.hs.mirim.family.entity.chore.Chore;
import kr.hs.mirim.family.entity.chore.ChoreCategory;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
public interface ChoreRepository extends CrudRepository<Chore, Long> {
    boolean existsByChoreDateAndChoreCategoryAndUser_UserId(LocalDate date, ChoreCategory choreCategory, Long userId);
}