package com.sparta.codeplanet.product.repository.reply;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.codeplanet.product.entity.Reply;
import com.sparta.codeplanet.product.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Objects;

import static com.sparta.codeplanet.product.entity.QReply.reply;

@RequiredArgsConstructor
public class ReplyRepositoryQueryImpl implements ReplyRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Reply> getLikeReplies(User user, Pageable pageable) {
        List<Reply> replies = jpaQueryFactory
                .select(reply)
                .from(reply)
                .where(likeUserEq(user))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reply.createdAt.desc())
                .fetch();

        Long totalSize = countQuery(user).fetch().get(0);

        return PageableExecutionUtils.getPage(replies, pageable, ()->totalSize);
    }

    private BooleanExpression likeUserEq(User user) {
        return Objects.nonNull(user) ? reply.likesList.any().user.eq(user) : null;
    }

    private JPAQuery<Long> countQuery(User user) {
        return jpaQueryFactory.select(Wildcard.count)
                .from(reply)
                .where(likeUserEq(user));
    }
}
