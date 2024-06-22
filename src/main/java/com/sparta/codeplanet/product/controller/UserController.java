package com.sparta.codeplanet.product.controller;

import com.sparta.codeplanet.product.dto.SignupRequestDto;
import com.sparta.codeplanet.product.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public String signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult ) {
        /** Validation 예외처리  dto로처리할수있음**/
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "error";
        }

        userService.signup(requestDto);



        return "login";
    }

    @PostMapping("users/logout")
    public void signout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.clearContext();
        //토큰들어오면 수정
        //userService.deleteRefreshToken(authentication.getName());
    }
}
