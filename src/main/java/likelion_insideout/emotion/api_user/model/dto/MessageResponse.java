package likelion_insideout.emotion.api_user.model.dto;

import lombok.*;
import org.aspectj.bridge.IMessage;
import org.aspectj.bridge.Message;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    private String message;
    private String email;
}