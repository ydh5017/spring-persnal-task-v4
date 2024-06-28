package com.sparta.codeplanet.product.repository.reply;

import com.sparta.codeplanet.product.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long>, ReplyRepositoryQuery {

    List<Reply> findAllByFeedId(long feedId);
}
