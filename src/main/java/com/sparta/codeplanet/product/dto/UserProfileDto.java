package com.sparta.codeplanet.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileDto {

    private String companyName;
    private String email;
    private String nickname;
    private String intro;
    private Long likeFeedCnt;
    private Long likeReplyCnt;

    @Builder
    public UserProfileDto(String companyName, String email, String nickname, String intro, Long likeFeedCnt, Long likeReplyCnt) {
        this.companyName = companyName;
        this.email = email;
        this.nickname = nickname;
        this.intro = intro;
        this.likeFeedCnt = likeFeedCnt;
        this.likeReplyCnt = likeReplyCnt;
    }
}
