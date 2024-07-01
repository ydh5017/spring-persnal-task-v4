package com.sparta.codeplanet.product.controller;

import com.sparta.codeplanet.global.enums.ResponseMessage;
import com.sparta.codeplanet.global.security.UserDetailsImpl;
import com.sparta.codeplanet.product.dto.ResponseEntityDto;
import com.sparta.codeplanet.product.dto.UpdatePasswordReq;
import com.sparta.codeplanet.product.service.UserService;
import com.sparta.codeplanet.product.service.UserUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping
    public ResponseEntity<?> updateProfile(
            @RequestBody @Valid UserUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(userService.updateProfile(userDetails.getUser().getId(), requestDto));
    }

    @PatchMapping
    public ResponseEntity<?> updatePassword (
            @Valid @RequestBody UpdatePasswordReq updatePasswordReq,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updatePassword(userDetails.getUser(), updatePasswordReq);
        return ResponseEntity.ok("Password updated");
    }

    /**
     * 프로필 조회
     * @param userDetails 회원 정보
     * @return 프로필 정보
     */
    @GetMapping
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(
                new ResponseEntityDto<>(
                        ResponseMessage.USER_READ_SUCCESS,
                        userService.getProfile(userDetails.getUser())
                )
        );
    }
}
