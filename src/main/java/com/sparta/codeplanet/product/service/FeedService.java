package com.sparta.codeplanet.product.service;

import com.sparta.codeplanet.global.enums.ErrorType;
import com.sparta.codeplanet.global.enums.ResponseMessage;
import com.sparta.codeplanet.global.exception.CustomException;
import com.sparta.codeplanet.product.dto.FeedRequestDto;
import com.sparta.codeplanet.product.dto.FeedResponseDto;
import com.sparta.codeplanet.product.dto.ResponseEntityDto;
import com.sparta.codeplanet.product.entity.Feed;
import com.sparta.codeplanet.product.entity.User;
import com.sparta.codeplanet.product.repository.FeedRepository;
import com.sparta.codeplanet.product.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;


    public FeedService(FeedRepository feedRepository, UserRepository userRepository) {
        this.feedRepository = feedRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public FeedResponseDto createFeed(FeedRequestDto requestDto, User user) {
        Feed feed = new Feed(user, requestDto.getTitle(), requestDto.getContent(), requestDto.getStatus());
        Feed savedFeed = feedRepository.save(feed);
        return new FeedResponseDto(savedFeed);
    }


    @Transactional(readOnly = true)
    public List<FeedResponseDto> getFeeds(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return feedRepository.findAll(pageable)
            .getContent()
            .stream()
            .map(FeedResponseDto::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntityDto<FeedResponseDto> updateFeed(Long feedId, FeedRequestDto requestDto) {
        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_FEED));
        feed.update(requestDto.getTitle(), requestDto.getContent());
        FeedResponseDto feedResponse = new FeedResponseDto(feed);
        return new ResponseEntityDto<>(ResponseMessage.FEED_UPDATE_SUCCESS, feedResponse);
    }

    @Transactional
    public ResponseEntityDto<Void> deleteFeed(Long feedId) {
        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_FEED));
        feedRepository.delete(feed);
        return new ResponseEntityDto<>(ResponseMessage.FEED_DELETE_SUCCESS, null);
    }
}
