package likelion_insideout.emotion.api_user.model.service;

import likelion_insideout.emotion.api_user.model.dto.*;
import likelion_insideout.emotion.api_user.model.repository.apiUserRepository;
import likelion_insideout.emotion.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private apiUserRepository apiUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        apiUserRepository.save(user); // 사용자 저장
    }

    public MessageResponse registerUser(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getPasswordChk())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhone())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        apiUserRepository.save(user);
        return new MessageResponse("회원가입이 완료되었습니다.");
    }

    public TokenResponse loginUser(LoginRequest loginRequest) {
        // Implement token generation logic here
        return new TokenResponse("Token");
    }

    public MessageResponse logoutUser() {
        // Implement logout logic here
        return new MessageResponse("성공적으로 로그아웃 되었습니다.");
    }

    public FindIdDto findId(FindIdDto findIdRequest) {
        User user = apiUserRepository.findByNameAndPhoneNumber(findIdRequest.getName(), findIdRequest.getPhone())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new FindIdDto(user.getEmail());
    }

    public MessageResponse sendTemporaryPassword(SendTempPasswordRequest sendTempPasswordRequest) {
        // Implement sending temporary password logic here
        return new MessageResponse("임시 비밀번호가 발송되었습니다.");
    }

    public MessageResponse resetPassword(ResetPasswordDto resetPasswordRequest) {
        // Implement password reset logic here
        return new MessageResponse("비밀번호가 성공적으로 변경되었습니다.");
    }

    public UserInfoResponse getUserInfo() {
        // Fetch the currently logged-in user info
        return new UserInfoResponse("exampleUser", "exampleNickName", "user@example.com");
    }

    public EmotionResponse getUserEmotion() {
        // Fetch the most used emotion
        return new EmotionResponse("기쁨");
    }

    public MessageResponse updateUserInfo(UpdateUserRequest updateUserRequest) {
        // Implement user info update logic here
        return new MessageResponse("수정되었습니다.");
    }
}
