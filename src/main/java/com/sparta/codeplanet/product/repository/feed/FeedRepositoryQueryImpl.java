package com.sparta.codeplanet.product.repository.feed;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.codeplanet.product.entity.Feed;
import com.sparta.codeplanet.product.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Objects;

import static com.sparta.codeplanet.product.entity.QFeed.feed;

@RequiredArgsConstructor
public class FeedRepositoryQueryImpl implements FeedRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Feed> getLikeFeeds(User user, Pageable pageable) {
        JPAQuery<Feed> query = query(feed, user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(feed.createdAt.desc());

        List<Feed> feeds = query.fetch();
        Long totalSize = countQuery(user).fetch().get(0);

        return PageableExecutionUtils.getPage(feeds, pageable, ()-> totalSize);
    }

    private <T> JPAQuery<T> query(Expression<T> expr, User user) {
        return jpaQueryFactory.select(expr)
                .from(feed)
                .leftJoin(feed.likesList).fetchJoin()
                .where(
                        likeUserEq(user)
                );
    }

    private JPAQuery<Long> countQuery(User user) {
        return jpaQueryFactory.select(Wildcard.count)
                .from(feed)
                .where(
                        likeUserEq(user)
                );
    }

    private BooleanExpression likeUserEq(User user) {
        return Objects.nonNull(user) ? feed.likesList.any().user.eq(user) : null;
    }
}
