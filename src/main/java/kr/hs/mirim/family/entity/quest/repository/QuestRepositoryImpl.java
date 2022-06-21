package kr.hs.mirim.family.entity.quest.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.mirim.family.dto.response.MonthQuestKingResponse;
import kr.hs.mirim.family.dto.response.UserQuestKingResponse;
import kr.hs.mirim.family.entity.chore.Chore;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.YearMonth;

import static kr.hs.mirim.family.entity.quest.QQuest.*;
import static kr.hs.mirim.family.entity.user.QUser.*;

public class QuestRepositoryImpl extends QuerydslRepositorySupport implements QuestRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    public QuestRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Chore.class);
        this.queryFactory = jpaQueryFactory;
    }

    public UserQuestKingResponse userQuestKing(long groupId, YearMonth date){

        return queryFactory
                .select(Projections.constructor(
                        UserQuestKingResponse.class,
                        quest.acceptUserId.as("userId"),
                        quest.count().as("count")
                ))
                .from(quest)
                .groupBy(quest.acceptUserId)
                .where(quest.group.groupId.eq(groupId),
                        quest.completeCheck.eq(true),
                        quest.createdDate.month().eq(date.getMonthValue()),
                        quest.modifiedDate.year().eq(date.getYear()))
                .fetchFirst();
    }

    @Override
    public MonthQuestKingResponse monthQuestKing(long groupId, YearMonth date) {
        return queryFactory
                .select(Projections.fields(
                        MonthQuestKingResponse.class,
                        quest.acceptUserId.as("userId"),
                        user.userNickname,
                        user.userImagePath,
                        quest.questId.count().as("count")

                ))
                .from(quest, user)
                .groupBy(quest.acceptUserId)
                .where(user.userId.eq(quest.acceptUserId),
                        quest.group.groupId.eq(groupId),
                        quest.completeCheck.eq(true),
                        quest.createdDate.month().eq(date.getMonthValue()),
                        quest.modifiedDate.year().eq(date.getYear()))
                .fetchFirst();
    }
}
