package com.sparta.codeplanet.product.repository.likes;

import com.sparta.codeplanet.product.entity.likes.ReplyLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikesRepository extends JpaRepository<ReplyLikes, Integer> {

    ReplyLikes findByReplyIdAndUserId(long replyId, long userId);

}
