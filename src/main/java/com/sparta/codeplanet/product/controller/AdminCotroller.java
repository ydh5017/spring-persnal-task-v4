package com.sparta.codeplanet.product.controller;

import com.sparta.codeplanet.global.enums.ResponseMessage;
import com.sparta.codeplanet.global.security.UserDetailsImpl;
import com.sparta.codeplanet.product.dto.CompanyRequestDto;
import com.sparta.codeplanet.product.dto.ResponseEntityDto;
import com.sparta.codeplanet.product.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminCotroller {

    private final AdminService adminService;

    @PostMapping("/company")
    public ResponseEntity<?> createCompany(
            @RequestBody CompanyRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("createCompany@@@@");

//        return ResponseEntity.ok(
//                new ResponseEntityDto<>(
//                        ResponseMessage.ADD_COMPANY_SUCCESS,
//                        adminService.createCompany(requestDto, userDetails.getUser())
//                )
//        );
        return ResponseEntity.ok("success");
    }
}
