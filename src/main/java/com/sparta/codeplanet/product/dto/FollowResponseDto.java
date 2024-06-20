package com.sparta.codeplanet.product.dto;

import com.sparta.codeplanet.product.entity.User;
import lombok.Getter;

@Getter
public class FollowResponseDto {

    private Long userId;
    private String companyName;
    private String userName;
    private String userIntro;
    private String userEmail;
    private String followStatus;

    public FollowResponseDto(User user) {
        this.userId = user.getId();
        this.companyName = user.getCompany().getName();
        this.userName = user.getNickname();
        this.userIntro = user.getIntro();
        this.userEmail = user.getEmail();
    }

    /**
     * 팔로우 여부
     * @param email 이메일
     */
    public void setFollowStatus(String email) {
        if (this.userEmail.equals(email)) {
            this.followStatus = "Followed";
        } else {
            this.followStatus = "Not Followed";
        }
    }
}
