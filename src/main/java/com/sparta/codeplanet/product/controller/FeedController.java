package com.sparta.codeplanet.product.controller;


import com.sparta.codeplanet.global.enums.ResponseMessage;
import com.sparta.codeplanet.global.security.UserDetailsImpl;
import com.sparta.codeplanet.product.dto.FeedRequestDto;
import com.sparta.codeplanet.product.dto.FeedResponseDto;
import com.sparta.codeplanet.product.dto.ResponseEntityDto;
import com.sparta.codeplanet.product.entity.Feed;
import com.sparta.codeplanet.product.service.FeedService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping
    public ResponseEntity<ResponseEntityDto<FeedResponseDto>> createFeed(
        @RequestBody FeedRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        FeedResponseDto responseDto = feedService.createFeed(requestDto, userDetails.getUser());
        ResponseEntityDto<FeedResponseDto> responseEntity = new ResponseEntityDto<>(ResponseMessage.FEED_CREATE_SUCCESS, responseDto);
        return ResponseEntity.ok(responseEntity);
    }

    @PutMapping("/{feedId}")
    public ResponseEntity<ResponseEntityDto<FeedResponseDto>> updateFeed(
        @PathVariable Long feedId,
        @RequestBody FeedRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        FeedResponseDto responseDto = feedService.updateFeed(feedId, requestDto, userDetails.getUser())
            .getData();
        ResponseEntityDto<FeedResponseDto> responseEntity = new ResponseEntityDto<>(ResponseMessage.FEED_UPDATE_SUCCESS, responseDto);
        return ResponseEntity.ok(responseEntity);
    }
}
