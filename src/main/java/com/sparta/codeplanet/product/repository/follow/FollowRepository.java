package com.sparta.codeplanet.product.repository.follow;

import com.sparta.codeplanet.product.entity.Follow;
import com.sparta.codeplanet.product.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryQuery {
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);
}
