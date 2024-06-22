package com.sparta.codeplanet.product.controller;

import com.sparta.codeplanet.product.dto.SignupRequestDto;
import com.sparta.codeplanet.product.dto.UpdatePasswordReq;
import com.sparta.codeplanet.product.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
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

    @PostMapping("users/signout")
    public void signout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    @PatchMapping("")
    public void updatePassword (@Valid @RequestBody UpdatePasswordReq updatePasswordReq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userService.updatePassword(email, updatePasswordReq);
    }

}
