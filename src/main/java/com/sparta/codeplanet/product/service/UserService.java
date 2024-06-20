package com.sparta.codeplanet.product.service;

import com.sparta.codeplanet.global.enums.UserRole;
import com.sparta.codeplanet.product.dto.SignupRequestDto;
import com.sparta.codeplanet.product.dto.UserRequestDto;
import com.sparta.codeplanet.product.entity.User;
import com.sparta.codeplanet.product.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;



    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String nickname = requestDto.getNickname();
        String email = requestDto.getEmail();
        String companyId = requestDto.getCompanyId();
        String intro = requestDto.getIntro();


        // username 유효성 검사
        if (!username.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{10,20}$")) {
            throw new IllegalArgumentException("아이디는 최소 10글자 이상, 20자 이하이며 대소문자 포함 영문 + 숫자만을 허용합니다.");
        }

        // password 유효성 검사
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+\\\\|\\[\\]{};:'\",.<>\\/?]).{10,}$")) {
            throw new IllegalArgumentException("비밀번호는 최소 10자 이상, 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.");
        }

        // 회원 중복 확인
        Optional<User> checkUserId = userRepository.findByUsername(username);
        if (checkUserId.isPresent()) {
            throw new IllegalArgumentException("중복된 아이디 입니다.");
        }

        // 사용자 등록
//        String hashedPassword = passwordEncoder.encode(password);
//        User user = new User(username,hashedPassword,nickname,email,company,intro);
//        userRepository.save(user);
//        System.out.println(user);
    }
    @Transactional
    public String updateUser(Long id, UserRequestDto requestDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String userInputPassword = requestDto.getPassward();
            String storedPassword = user.getPassword();
//           if (!passwordEncoder.matches(userInputPassword, storedPassword)) {
//                return "비밀번호가 틀림";
//            }
//            if ("정상".equals(user.getStatus())) {
//                user.setRefreshToken(null);
//                user.setStatus("탈퇴");
//            } else if ("탈퇴".equals(user.getStatus())) {
//                throw new IllegalArgumentException("이미 탈퇴 됬지롱");
//            }
//            userRepository.save(user);
//        }
//        return "정상 탈퇴!";
//    }
//}


