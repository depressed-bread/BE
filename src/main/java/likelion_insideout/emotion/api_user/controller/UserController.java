package likelion_insideout.emotion.api_user.controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion_insideout.emotion.api_user.model.dto.*;
import likelion_insideout.emotion.api_user.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    public UserService userService;

    //회원가입
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    //아이디찾기
    @PostMapping("/find-id")
    public ResponseEntity<FindIdDto> findId(@RequestBody FindIdDto findIdRequest) {
       return ResponseEntity.ok(userService.findId(findIdRequest));
    }

    //임시비밀번호 발송
    @PostMapping("/send-temp-password")
    public ResponseEntity<MessageResponse> sendTemporaryPassword(@RequestBody SendTempPasswordRequest sendTempPasswordRequest) {
        MessageResponse response = userService.sendTemporaryPassword(String.valueOf(sendTempPasswordRequest));
        return ResponseEntity.ok(response);
    }

    //비밀번호 재설정
    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody ResetPasswordDto resetPasswordRequest) {
        return ResponseEntity.ok(userService.resetPassword(resetPasswordRequest));
    }

    //사용자 정보조회
    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo() {
        return ResponseEntity.ok(userService.getUserInfo());
    }

    //사용자대표이모티콘 조회
    @GetMapping("/emotion")
    public ResponseEntity<EmotionResponse> getUserEmotion() {
        return ResponseEntity.ok(userService.getUserEmotion());
    }

    //사용자 정보수정
    @PutMapping("/info")
    public ResponseEntity<MessageResponse> updateUserInfo(@RequestBody UpdateUserRequest updateUserRequest) {
        return ResponseEntity.ok(userService.updateUserInfo(updateUserRequest));
    }


}