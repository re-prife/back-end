package kr.hs.mirim.family.entity.chore.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.mirim.family.dto.response.KingResponse;
import kr.hs.mirim.family.entity.chore.Chore;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

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
    public List<KingResponse> monthKing(long groupId, YearMonth date) {
        return queryFactory
                .select(Projections.constructor(
                        KingResponse.class,
                        chore.choreCategory,
                        chore.user.userId,
                        chore.choreCategory.count().as("categoryList")
                ))
                .from(chore)
                .where(chore.group.groupId.eq(groupId), chore.choreDate.year().eq(date.getYear()), chore.choreDate.month().eq(date.getMonthValue()))
                .groupBy(chore.choreCategory, chore.user.userId)
                .orderBy(chore.choreCategory.asc())
                .orderBy(chore.choreCategory.count().desc())
                .fetch();
    }
}
