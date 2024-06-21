package com.sparta.codeplanet.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS(""),
    ERROR("유효하지 않은 접근입니다."),
    SEND_EMAIL("승인 코드가 발송되었습니다."),
    VERIFY_AUTH_CODE("회원가입 승인 처리되었습니다."),
    SUCCESS_LOGIN("로그인 완료"),
    FOLLOW("님을 팔로우 하였습니다."),
    UNFOLLOW("님을 언팔로우 하였습니다."),
    FOLLOWING_LIST("팔로잉 목록 조회에 성공하였습니다."),
    FOLLOWER_LIST("팔로워 목록 조회에 성공하였습니다.");


    private final String message;
}
