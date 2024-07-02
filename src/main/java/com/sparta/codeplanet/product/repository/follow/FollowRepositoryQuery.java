package com.sparta.codeplanet.product.repository.follow;

import com.sparta.codeplanet.product.entity.User;

import java.util.List;

public interface FollowRepositoryQuery {

    List<User> getFollowerTop10();
}
