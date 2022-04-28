package kr.hs.mirim.family.entity.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hs.mirim.family.dto.response.UserListResponse;
import kr.hs.mirim.family.entity.user.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static kr.hs.mirim.family.entity.user.QUser.user;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryExtension {
    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(User.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public List<UserListResponse> userList(Long groupId, Long userId) {
        return queryFactory.select(Projections.constructor(
                                UserListResponse.class,
                                user.userId,
                                user.userName,
                                user.userImageName
                        )
                )
                .from(user)
                .where(user.group.groupId.eq(groupId), user.userId.ne(userId))
                .fetch();
    }
}
