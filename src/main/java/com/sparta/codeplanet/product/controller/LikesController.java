package com.sparta.codeplanet.product.controller;

import com.sparta.codeplanet.global.enums.ResponseMessage;
import com.sparta.codeplanet.global.security.UserDetailsImpl;
import com.sparta.codeplanet.product.dto.ResponseEntityDto;
import com.sparta.codeplanet.product.service.LikesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feed/{feedId}")
public class LikesController {
    private  final LikesService likesService;

    public LikesController(final LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping("/likes")
    public ResponseEntity<?> addLikesToFeed(
            @PathVariable("feedId") long feedId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new ResponseEntityDto<>(
                        ResponseMessage.ADD_LIKE_TO_FEED_SUCCESS,
                        likesService.likeFeed(feedId, userDetails)
                )
        );
    }

    @DeleteMapping("/likes")
    public ResponseEntity<?> subLikesToFeed(
            @PathVariable("feedId") long feedId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new ResponseEntityDto<>(
                        ResponseMessage.CANCEL_LIKE_TO_FEED_SUCCESS,
                        likesService.unlikeFeed(feedId, userDetails)
                )
        );
    }

    @PostMapping("/reply/{replyId}/likes")
    public ResponseEntity<?> addLikesToReply(
            @PathVariable("feedId") long feedId,
            @PathVariable("replyId") int replyId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new ResponseEntityDto<>(
                        ResponseMessage.ADD_LIKE_TO_REPLY_SUCCESS,
                        likesService.likeReply(replyId, userDetails)
                )
        );
    }

    @DeleteMapping("/reply/{replyId}/likes")
    public ResponseEntity<?> subLikesToReply(
            @PathVariable("feedId") long feedId,
            @PathVariable("replyId") int replyId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new ResponseEntityDto<>(
                        ResponseMessage.CANCEL_LIKE_TO_REPLY_SUCCESS,
                        likesService.unlikeReply(replyId, userDetails)
                )
        );
    }


}
