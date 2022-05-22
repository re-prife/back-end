package kr.hs.mirim.family.entity.chore.repository;

import kr.hs.mirim.family.entity.chore.Chore;
import org.springframework.data.repository.CrudRepository;

public interface ChoreRepository extends CrudRepository<Chore, Long>, ChoreRepositoryExtension {

}
