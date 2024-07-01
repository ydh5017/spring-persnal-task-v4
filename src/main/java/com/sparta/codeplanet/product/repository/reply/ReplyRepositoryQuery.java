package com.sparta.codeplanet.product.repository.reply;

import com.sparta.codeplanet.product.entity.Reply;
import com.sparta.codeplanet.product.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyRepositoryQuery {

    Page<Reply> getLikeReplies(User user, Pageable pageable);

    Long countLikeReplies(User user);
}
