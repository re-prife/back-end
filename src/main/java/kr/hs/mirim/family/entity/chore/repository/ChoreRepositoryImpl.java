package kr.hs.mirim.family.entity.chore.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.mirim.family.dto.response.ChoreListOneDayDataResponse;
import kr.hs.mirim.family.entity.chore.Chore;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static kr.hs.mirim.family.entity.chore.QChore.chore;

public class ChoreRepositoryImpl extends QuerydslRepositorySupport implements ChoreRepositoryExtension{
    private final JPAQueryFactory queryFactory;

    public ChoreRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Chore.class);
        this.queryFactory = jpaQueryFactory;
    }


    @Override
    public List<ChoreListOneDayDataResponse> findByChoreGroupAndDate(Long groupId, LocalDate date) {
        return queryFactory
                .select(Projections.constructor(
                        ChoreListOneDayDataResponse.class,
                        chore.choreId,
                        chore.user.userId,
                        chore.choreTitle,
                        chore.choreCategory,
                        chore.choreDate
                        ))
                .from(chore)
                .where(chore.choreDate.eq(date), chore.user.group.groupId.eq(groupId))
                .fetch();
    }
}
