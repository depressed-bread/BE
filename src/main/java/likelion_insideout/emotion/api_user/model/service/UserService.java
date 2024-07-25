package likelion_insideout.emotion.api_user.model.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import likelion_insideout.emotion.api_analysis.model.dto.ExpenseDto;
import likelion_insideout.emotion.api_user.model.dto.*;
import likelion_insideout.emotion.api_user.model.repository.apiUserRepository;
import likelion_insideout.emotion.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    public apiUserRepository apiUserRepository;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

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

    public FindIdDto findId(FindIdDto findIdRequest) {
        User user = apiUserRepository.findByNameAndPhone(findIdRequest.getName(), findIdRequest.getPhone())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을수 없습니다."));
        return new FindIdDto("email" + user.getEmail());
    }

    // 임시 비밀번호 생성 메소드
    public String generateTempPassword(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    //DB 에 임시비밀번호 업데이트 메소드
    private boolean updateTemporaryPassword(String email, String tempPassword) {
        User user = apiUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodePw = encoder.encode(tempPassword); // 패스워드 암호화
            user.updatePassword(encodePw);
            apiUserRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public MessageResponse sendTemporaryPassword(String memberEmail) {
        String tempPassword = generateTempPassword(10);  // 10자리 임시 비밀번호 생성
        if (updateTemporaryPassword(memberEmail, tempPassword)) { // DB에 임시 비밀번호 업데이트
            sendEmail(memberEmail, "임시 비밀번호 발급", "귀하의 임시 비밀번호는 " + tempPassword + " 입니다. 로그인 후 비밀번호를 변경해주세요.");
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

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePw = encoder.encode(resetPasswordRequest.getNewPassword()); // 패스워드 암호화
        user.updatePassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        apiUserRepository.save(user);

        return MessageResponse.builder()
                .message("비밀번호가 성공적으로 변경되었습니다.")
                .build();
    }

    public UserInfoResponse getUserInfo() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();

        return new UserInfoResponse(getUserInfo().getEmail());
    }

    public EmotionResponse getUserEmotion() {
        // Fetch the most used emotion
        return new EmotionResponse("기쁨");
    }

    public MessageResponse updateUserInfo(UpdateUserRequest updateUserRequest) {
        // Implement user info update logic here
        return MessageResponse.builder()
                .message("정보가 성공적으로 업데이트되었습니다.")
                .build();
    }
}
