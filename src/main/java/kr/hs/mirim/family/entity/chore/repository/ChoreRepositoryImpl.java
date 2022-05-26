package kr.hs.mirim.family.entity.chore.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.mirim.family.dto.response.ChoreKingResponse;
import kr.hs.mirim.family.dto.response.KingResponse;
import kr.hs.mirim.family.dto.response.ChoreListDataResponse;
import kr.hs.mirim.family.entity.chore.Chore;
import kr.hs.mirim.family.entity.chore.ChoreCheck;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static kr.hs.mirim.family.entity.chore.QChore.*;

public class ChoreRepositoryImpl extends QuerydslRepositorySupport implements ChoreRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    public ChoreRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Chore.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public List<ChoreKingResponse> monthKing(long groupId, YearMonth date) {
        return queryFactory
                .select(Projections.constructor(
                        ChoreKingResponse.class,
                        chore.choreCategory,
                        chore.user.userId,
                        chore.choreCategory.count().as("questCount")
                ))
                .from(chore)
                .where(chore.group.groupId.eq(groupId), chore.choreDate.year().eq(date.getYear()), chore.choreDate.month().eq(date.getMonthValue()), chore.choreCheck.eq(ChoreCheck.SUCCESS))
                .groupBy(chore.choreCategory, chore.user.userId)
                .orderBy(chore.choreCategory.asc())
                .orderBy(chore.choreCategory.count().desc())
                .fetch();
    }
  
   @Override
    public List<ChoreListDataResponse> findByChoreGroup_GroupIdAndDate(Long groupId, LocalDate date) {
        return queryFactory
                .select(Projections.constructor(
                        ChoreListDataResponse.class,
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

    @Override
    public List<ChoreListDataResponse> findByChoreGroup_GroupIdAndDateMonth(Long groupId, YearMonth date) {
        return queryFactory
                .select(Projections.constructor(
                        ChoreListDataResponse.class,
                        chore.choreId,
                        chore.user.userId,
                        chore.choreTitle,
                        chore.choreCategory,
                        chore.choreDate
                ))
                .from(chore)
                .where(chore.user.group.groupId.eq(groupId), chore.choreDate.year().eq(date.getYear()), chore.choreDate.month().eq(date.getMonthValue()))
                .fetch();
    }
}
