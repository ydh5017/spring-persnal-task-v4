package com.sparta.codeplanet.product.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    private String username;
    private String email;
    private String intro;
    private String nickname;

    // ture 들어오면 탈퇴
    private Boolean isTalTye;
}

