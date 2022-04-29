package kr.hs.mirim.family.entity.group.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.mirim.family.entity.group.Group;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class GroupRepositoryImpl extends QuerydslRepositorySupport implements GroupRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    public GroupRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Group.class);
        this.queryFactory = jpaQueryFactory;
    }
}
