package com.sparta.codeplanet.product.dto;

import com.sparta.codeplanet.product.entity.User;
import lombok.Getter;

@Getter
public class FollowerTop10Dto {

    private Long userId;
    private String companyName;
    private String userName;
    private String userIntro;
    private String userEmail;
    private Long followerCnt;

    public FollowerTop10Dto(User user) {
        this.userId = user.getId();
        this.companyName = user.getCompany().getName();
        this.userName = user.getUsername();
        this.userIntro = user.getIntro();
        this.userEmail = user.getEmail();
        this.followerCnt = (long) user.getFollowerList().size();
    }
}
