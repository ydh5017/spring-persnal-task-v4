package com.sparta.codeplanet.product.repository.feed;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.codeplanet.product.dto.PageDTO;
import com.sparta.codeplanet.product.entity.Feed;
import com.sparta.codeplanet.product.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Objects;

import static com.sparta.codeplanet.product.entity.QFeed.feed;
import static com.sparta.codeplanet.product.entity.QFollow.follow;

@RequiredArgsConstructor
@Slf4j
public class FeedRepositoryQueryImpl implements FeedRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Feed> getLikeFeeds(User user, Pageable pageable) {
        JPAQuery<Feed> query = getLikeFeedsQuery(feed, user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(feed.createdAt.desc());

        List<Feed> feeds = query.fetch();
        Long totalSize = countQuery(user).fetch().get(0);

        return PageableExecutionUtils.getPage(feeds, pageable, ()-> totalSize);
    }

    @Override
    public Long countLikeFeeds(User user) {
        return countQuery(user).fetch().get(0);
    }

    @Override
    public List<Feed> getFollowingFeeds(User fromUser, PageDTO pageDTO) {
        log.info(pageDTO.getSortBy());
        JPAQuery<Feed> query = getFollowingFeedQuery(feed, fromUser)
                .offset(pageDTO.toPageable().getOffset())
                .limit(pageDTO.toPageable().getPageSize())
                .orderBy(sortByField(pageDTO));
        return query.fetch();
    }

    private OrderSpecifier<?> sortByField(PageDTO pageDTO) {
        if ("title".equals(pageDTO.getSortBy())) {
            return feed.title.desc();
        }
        if ("content".equals(pageDTO.getSortBy())) {
            return feed.content.desc();
        }
        return feed.createdAt.desc();
    }

    private <T> JPAQuery<T> getLikeFeedsQuery(Expression<T> expr, User user) {
        return jpaQueryFactory.select(expr)
                .from(feed)
                .leftJoin(feed.likesList).fetchJoin()
                .where(
                        likeUserEq(user)
                );
    }

    private <T> JPAQuery<T> getFollowingFeedQuery(Expression<T> expr, User fromUser) {
        return jpaQueryFactory.select(expr)
                .from(feed)
                .where(
                        feed.user.in(
                                JPAExpressions
                                        .select(follow.toUser)
                                        .from(follow)
                                        .where(follow.fromUser.eq(fromUser))
                        )
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
