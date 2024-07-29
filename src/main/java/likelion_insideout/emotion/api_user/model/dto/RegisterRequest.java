package likelion_insideout.emotion.api_user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import likelion_insideout.emotion.entity.User;
import likelion_insideout.emotion.entity.enums.EmotionType;
import likelion_insideout.emotion.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String passwordChk;

    public User toEntity(){
        return User.builder()
                .password(this.password)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .role(Role.USER)
                .emotion(EmotionType.DEPRESSION)
                .build();
    }
}
