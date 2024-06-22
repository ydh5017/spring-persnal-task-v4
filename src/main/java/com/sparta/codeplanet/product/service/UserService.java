package com.sparta.codeplanet.product.service;

import com.sparta.codeplanet.global.enums.ErrorType;
import com.sparta.codeplanet.global.enums.Status;
import com.sparta.codeplanet.global.exception.CustomException;
import com.sparta.codeplanet.product.dto.SignupRequestDto;
import com.sparta.codeplanet.product.dto.UpdatePasswordReq;
import com.sparta.codeplanet.product.entity.User;
import com.sparta.codeplanet.product.entity.UserPasswordLog;
import com.sparta.codeplanet.product.repository.UserPasswordLogRepersitory;
import com.sparta.codeplanet.product.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPasswordLogRepersitory userPasswordLogRepersitory;
    private PasswordEncoder passwordEncoder;

    /**
     * 이메일로 회원 찾기
     *
     * @param email 이메일
     * @return 회원
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_USER));
    }

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String nickname = requestDto.getNickname();
        String email = requestDto.getEmail();
       // String companyId = requestDto.getCompanyId();
        String intro = requestDto.getIntro();


        // username 유효성 검사 -> DTO, 유저를 만들때 들어가는게 좋다
        if (!username.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{10,20}$")) {
            throw new IllegalArgumentException("아이디는 최소 10글자 이상, 20자 이하이며 대소문자 포함 영문 + 숫자만을 허용합니다.");
        }

        // password 유효성 검사
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+\\\\|\\[\\]{};:'\",.<>\\/?]).{10,}$")) {
            throw new IllegalArgumentException("비밀번호는 최소 10자 이상, 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.");
        }

        // 회원 중복 확인
        Optional<User> checkUserId = userRepository.findUserByUsername(username);
        if (checkUserId.isPresent()) {
            throw new IllegalArgumentException("중복된 아이디 입니다.");
        }
//
//        String hashedPassword = passwordEncoder.encode(password);
//        User user = new User(username, hashedPassword, nickname, email, companyId, intro);
//        userRepository.save(user);
//        System.out.println(user);
    }

    @Transactional
    public String updateUser(Long id, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(()-> new CustomException(ErrorType.NOT_FOUND_USER));
        //1. 프로필수정
        if(!requestDto.getIsTalTye()) {
        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        String intro = requestDto.getIntro();
        user.setUsername(username);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setIntro(intro);
        userRepository.save(user);

        return "수정성공";
        }


        //2. 탈퇴
        // 유저의 상태가 정상이면 탈퇴 할수 있음
        if(requestDto.getIsTalTye()) {
        if(user.getStatus().equals(Status.ACTIVE)){
            user.setStatus("탈퇴");
            userRepository.save(user);
            return "탈퇴성공";
        }

        // 유저의 상태가 정상이 아니면 탈퇴 할 수 없음
        if(user.getStatus().equals(Status.DEACTIVATE)){
            throw new CustomException(ErrorType.ALREADY_TALYE_USER);
        }

        }
        return "끝";
    }

    @Transactional
    public void updatePassword(String email, UpdatePasswordReq updatePasswordReq) {
        // 1.이메일로 유저를 가져온다
        // 2.유저가 사용한 최근 3개의 비밀번호와 새로운 비밀번호 (newPassword) 가 일치하는지 알려준다. 일치하지 않으면 안돼 임마 보냄
        // 3.유저가 입력한 현재 비밀번호와 db에 있는 유저의 비밀번호가 일치한지 확인한다 일치 하지 않으면 안돼 임마를 보냄
        // 4.유저의 비밀번호를 수정한다

        // 1.이메일로 유저를 가져온다
        User user = userRepository.findByEmail(email).orElseThrow(()-> new CustomException(ErrorType.NOT_FOUND_USER));

        //2 이전 3개의 비밀번호와 newPassword랑 겹치는거 있는지 확인
        List<UserPasswordLog> passwordLogs = userPasswordLogRepersitory.findTop3ByUserIdOrderByCreateAtDesc(user.getId());

        for(int i=0; i < passwordLogs.size(); i++) {
            UserPasswordLog passwordLog = passwordLogs.get(i);
            if(passwordEncoder.matches(passwordLog.getPassword(),passwordLog.getPassword())) {}
        }

        //3.currentPassword 현재 user 패스워드 같은지 확인
        //3-1 지금 로그인한 유저의 비밀번호
        String userPassword = user.getPassword();
        String currentPassword = updatePasswordReq.getCurrentPassword();

        if(!userPassword.matches(currentPassword)) {
            throw new RuntimeException();
        }

        //4.드디어 비밀번호 바꿈 ㅜㅠ
        String hashedPassword = passwordEncoder.encode(updatePasswordReq.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }


}
