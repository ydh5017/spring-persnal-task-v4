package com.sparta.codeplanet.product.service;

import com.sparta.codeplanet.global.enums.ErrorType;
import com.sparta.codeplanet.global.exception.CustomException;
import com.sparta.codeplanet.product.dto.PageDTO;
import com.sparta.codeplanet.product.dto.ReplyRequestDto;
import com.sparta.codeplanet.product.dto.ReplyResponseDto;
import com.sparta.codeplanet.product.entity.Feed;
import com.sparta.codeplanet.product.entity.Reply;
import com.sparta.codeplanet.product.entity.User;
import com.sparta.codeplanet.product.repository.feed.FeedRepository;
import com.sparta.codeplanet.product.repository.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final FeedRepository feedRepository;

    public ReplyResponseDto createReply(long feedId, ReplyRequestDto replyRequestDto, User user) {
        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_FEED));

        Reply reply = new Reply(replyRequestDto, feed, user);
        Reply saveReply = replyRepository.save(reply);

        return new ReplyResponseDto(saveReply);
    }

    public void deleteReply(long feedId, long replyId, User user) {
        Reply reply = replyRepository.findById(replyId)
            .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_REPLY));

        if (reply.getUser().getId() != user.getId()) {
            throw new CustomException(ErrorType.WRONG_USER_REPLY);
        }
        replyRepository.delete(reply);
    }

    @Transactional
    public ReplyResponseDto updateReply(long feedId, long replyId, ReplyRequestDto requestDto,
        User user) {
        Reply reply = replyRepository.findById(replyId)
            .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_REPLY));

        if (reply.getUser().getId() != user.getId()) {
            throw new CustomException(ErrorType.WRONG_USER_REPLY);
        }
        reply.update(requestDto);
        return new ReplyResponseDto(reply);
    }

    public List<ReplyResponseDto> findRepliesAll(long feedId) {
        Feed feed = feedRepository.findById(feedId)
            .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_FEED));

        return replyRepository.findAllByFeedId(feed.getId()).stream()
                .map(ReplyResponseDto::new)
                .toList();
    }

    /**
     * 댓글 단건 조회
     * @param replyId 댓글 ID
     * @return 댓글 정보
     */
    public ReplyResponseDto getReply(long replyId) {
        return replyRepository.findById(replyId)
                .map(ReplyResponseDto::new)
                .orElseThrow(()-> new CustomException(ErrorType.NOT_FOUND_REPLY));
    }

    /**
     * 로그인한 회원이 좋아요한 댓글 목록 조회
     * @param page 페이지 정보
     * @param user 회원 정보
     * @return 댓글 목록
     */
    public Page<ReplyResponseDto> getLikeReplies(PageDTO page, User user) {
        return replyRepository.getLikeReplies(user, page.toPageable()).map(ReplyResponseDto::new);
    }
}
