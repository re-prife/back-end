package kr.hs.mirim.family.entity.quest.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.mirim.family.dto.response.QuestKingResponse;
import kr.hs.mirim.family.entity.chore.Chore;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.*;
import java.time.YearMonth;

import static kr.hs.mirim.family.entity.quest.QQuest.*;

public class QuestRepositoryImpl extends QuerydslRepositorySupport implements QuestRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    public QuestRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Chore.class);
        this.queryFactory = jpaQueryFactory;
    }

    public List<QuestKingResponse> questKingMonth(long groupId, YearMonth date){

        return queryFactory
                .select(Projections.constructor(
                        QuestKingResponse.class,
                        quest.acceptUserId.as("userId"),
                        quest.count().as("count")
                ))
                .from(quest)
                .groupBy(quest.acceptUserId)
                .where(quest.group.groupId.eq(groupId), quest.completeCheck.eq(true), quest.createdDate.month().eq(date.getMonthValue()), quest.modifiedDate.year().eq(date.getYear()))
                .fetch();
    }
}
