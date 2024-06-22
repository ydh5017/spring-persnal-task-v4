package com.sparta.codeplanet.product.dto;

import lombok.*;


@Data
public class SignupRequestDto {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String companyId;
    private String intro;


}
