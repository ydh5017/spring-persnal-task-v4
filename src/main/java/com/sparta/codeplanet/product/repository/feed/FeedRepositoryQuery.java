package com.sparta.codeplanet.product.repository.feed;

import com.sparta.codeplanet.product.dto.PageDTO;
import com.sparta.codeplanet.product.entity.Feed;
import com.sparta.codeplanet.product.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedRepositoryQuery {

    Page<Feed> getLikeFeeds(User user, Pageable pageable);

    Long countLikeFeeds(User user);

    List<Feed> getFollowingFeeds(User fromUser, PageDTO pageDTO);
}
