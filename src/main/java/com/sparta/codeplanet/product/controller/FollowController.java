package com.sparta.codeplanet.product.controller;

import com.sparta.codeplanet.global.enums.ResponseMessage;
import com.sparta.codeplanet.product.dto.FollowResponseDto;
import com.sparta.codeplanet.product.dto.ResponseEntityDto;
import com.sparta.codeplanet.product.entity.User;
import com.sparta.codeplanet.product.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    /**
     * 팔로우 추가
     * @param userId 팔로우 요청 받은 회원 ID
     * @return 팔로우 요청 받은 회원의 이름
     */
    @PostMapping("/follow/{userId}")
    public ResponseEntity<?> createFollow(@PathVariable Long userId) {
        // todo : security 구현 완료 시 파라미터에 UserDetails 추가
        User user = new User();

        String followUsername = followService.createFollow(user, userId);

        return ResponseEntity.ok(ResponseMessage.FOLLOW.getMessage(followUsername));
    }

    /**
     * 팔로잉 목록 조회
     * @param userId 팔로잉 목록 조회할 회원 ID
     * @return 팔로잉 목록
     */
    @GetMapping("/{userId}/following")
    public ResponseEntity<?> getFollowingList(@PathVariable Long userId) {
        // todo : security 구현 완료 시 파라미터에 UserDetails 추가
        User user = new User();

        List<FollowResponseDto> responseDtoList = followService.getFollowingList(user, userId);

        return ResponseEntity.ok(
                new ResponseEntityDto<>(ResponseMessage.FOLLOWING_LIST, responseDtoList));
    }

    /**
     * 팔로워 목록 조회
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/follower")
    public ResponseEntity<?> getFollowerList(@PathVariable Long userId) {
        // todo : security 구현 완료 시 파라미터에 UserDetails 추가
        User user = new User();

        List<FollowResponseDto> responseDtoList = followService.getFollowerList(user, userId);

        return ResponseEntity.ok(
                new ResponseEntityDto<>(ResponseMessage.FOLLOWER_LIST, responseDtoList));
    }

    @DeleteMapping("/follow/{userId}")
    public ResponseEntity<?> deleteFollow(@PathVariable Long userId) {
        // todo : security 구현 완료 시 파라미터에 UserDetails 추가
        User user = new User();

        String unFollowUsername = followService.deleteFollow(user, userId);

        return ResponseEntity.ok(ResponseMessage.UNFOLLOW.getMessage(unFollowUsername));
    }
}
