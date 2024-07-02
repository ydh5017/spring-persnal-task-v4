package com.sparta.codeplanet.UnitTest.service;

import com.sparta.codeplanet.global.enums.Status;
import com.sparta.codeplanet.product.entity.Follow;
import com.sparta.codeplanet.product.entity.User;
import com.sparta.codeplanet.product.repository.follow.FollowRepository;
import com.sparta.codeplanet.product.repository.UserRepository;
import com.sparta.codeplanet.product.service.FollowService;
import com.sparta.codeplanet.product.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @InjectMocks
    private FollowService followService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowRepository followRepository;

    @Test
    void createFollow() {
        // given
        User user = user(1L);
        User user1 = user(2L);

        given(userService.getUserById(any()))
                .willReturn(user1);
        given(followRepository.findByFromUserAndToUser(any(), any()))
                .willReturn(Optional.empty());

        // when
        String nickname = followService.createFollow(user, user1.getId());

        // then
        assertEquals(nickname, user1.getNickname());
    }

    @Test
    void deleteFollow() {
        //given
        User user = user(1L);
        User user1 = user(2L);
        Follow follow = Follow.builder()
                .id(1L)
                .fromUser(user)
                .toUser(user1)
                .build();

        given(userService.getUserById(any()))
                .willReturn(user1);
        given(followRepository.findByFromUserAndToUser(any(), any()))
                .willReturn(Optional.of(follow));

        // when
        String nickname = followService.deleteFollow(user, user1.getId());

        // then
        assertEquals(nickname, user1.getNickname());
    }

    private User user(Long id) {
        return User.builder()
                .id(id)
                .username("test"+id+id+id+id+id+id)
                .email("test"+id+"@email.com")
                .nickname("test"+id)
                .status(Status.ACTIVE)
                .build();
    }
}