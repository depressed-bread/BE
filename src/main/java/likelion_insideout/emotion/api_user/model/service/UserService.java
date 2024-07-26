package likelion_insideout.emotion.api_user.model.service;

import jakarta.transaction.Transactional;
import likelion_insideout.emotion.api_user.model.dto.*;
import likelion_insideout.emotion.api_user.model.repository.apiUserRepository;
import likelion_insideout.emotion.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@Service
public class UserService {

    @Autowired
    public apiUserRepository apiUserRepository;
    public BCryptPasswordEncoder passwordEncoder;
    private JavaMailSender mailSender;
    private EmailValidator emailValidator;

    @Transactional
    public MessageResponse registerUser(RegisterRequest registerRequest) {
        if (apiUserRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User user= registerRequest.toEntity();
        apiUserRepository.save(user);

        return MessageResponse.builder()
                .message("회원가입이 완료되었습니다.")
                .email(registerRequest.getEmail())
                .build();
    }

    public String findId(FindIdDto findIdRequest) {
        User user = apiUserRepository.findByNameAndPhone(findIdRequest.getName(), findIdRequest.getPhone())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));
        return user.getEmail();  // 직접 이메일만 반환

    }

    // 임시 비밀번호 생성 메소드
    public String generateTempPassword(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    //DB 에 임시비밀번호 업데이트 메소드
    private boolean updateTemporaryPassword(String email, String tempPassword) {

        try {
            User user = apiUserRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            user.updatePassword(passwordEncoder.encode(tempPassword));
            apiUserRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public MessageResponse sendTemporaryPassword(String email) {
        String tempPassword = generateTempPassword(10);  // 10자리 임시 비밀번호 생성
        if (updateTemporaryPassword(email, tempPassword)) { // DB에 임시 비밀번호 업데이트
            sendEmail(email, "임시 비밀번호 발급", "귀하의 임시 비밀번호는 " + tempPassword + " 입니다. 로그인 후 비밀번호를 변경해주세요.");
            return MessageResponse.builder()
                    .message("임시 비밀번호가 발송되었습니다.")
                    .build();
        } else {
            return MessageResponse.builder()
                    .message("이메일 발송 실패. 사용자를 찾을 수 없습니다.")
                    .build();
        }

    }
    //이메일 발송 메서드
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    //비밀번호 재설정 로직
    public MessageResponse resetPassword(ResetPasswordDto resetPasswordRequest) {
        User user = apiUserRepository.findByEmail(resetPasswordRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        System.out.println("사용자 찾음: " + user.getEmail());
        return MessageResponse.builder()
                .message("비밀번호가 성공적으로 변경되었습니다.")
                .build();
    }

    public UserInfoResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 현재 인증된 사용자의 이메일(주로 사용자 이름으로 설정됩니다.)

        User user = apiUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new UserInfoResponse(user.getEmail());
    }

    public EmotionResponse getUserEmotion() {
        // Fetch the most used emotion
        return new EmotionResponse("기쁨");
    }

    public MessageResponse updateUserInfo(UpdateUserRequest updateUserRequest) {
        // 이메일로 사용자 찾기
        User user = apiUserRepository.findByEmail(updateUserRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 사용자 정보 업데이트 로직 (예: 이름, 전화번호)
        user.setName(updateUserRequest.getName());
        user.setPhone(updateUserRequest.getPhone());
        apiUserRepository.save(user); // 변경된 정보 저장

        return MessageResponse.builder()
                .message("정보가 성공적으로 업데이트되었습니다.")
                .email(user.getEmail())
                .build();
    }
}
