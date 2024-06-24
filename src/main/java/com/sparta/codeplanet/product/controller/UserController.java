package com.sparta.codeplanet.product.controller;

import com.sparta.codeplanet.global.security.UserDetailsImpl;
import com.sparta.codeplanet.product.dto.SignupRequestDto;
import com.sparta.codeplanet.product.dto.UpdatePasswordReq;
import com.sparta.codeplanet.product.service.UserService;
import com.sparta.codeplanet.product.service.UserUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("")
    public String signup(@RequestBody @Valid SignupRequestDto requestDto) {

//        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//        if(fieldErrors.size() > 0) {
//            for (FieldError fieldError : bindingResult.getFieldErrors()) {
//                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
//            }
//            return "error";
//        }
//
//        userService.signup(requestDto);
//
//

        return "singup";
    }

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userService.updatePassword(userDetails.getUser(), updatePasswordReq);
        return ResponseEntity.ok("Password updated");
    }

}
