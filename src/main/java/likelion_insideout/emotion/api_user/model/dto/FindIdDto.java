package likelion_insideout.emotion.api_user.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindIdDto {
    private String name;
    private String phone;

    public FindIdDto(String email) {;
    }
}