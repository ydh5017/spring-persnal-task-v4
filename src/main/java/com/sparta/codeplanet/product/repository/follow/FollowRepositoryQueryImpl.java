package com.sparta.codeplanet.product.repository.follow;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.codeplanet.product.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sparta.codeplanet.product.entity.QFollow.follow;

@RequiredArgsConstructor
public class FollowRepositoryQueryImpl implements FollowRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> getFollowerTop10() {
        return jpaQueryFactory.select(follow.toUser)
                .from(follow)
                .groupBy(follow.toUser)
                .orderBy(follow.toUser.count().desc())
                .limit(10L)
                .fetch();
    }
}
