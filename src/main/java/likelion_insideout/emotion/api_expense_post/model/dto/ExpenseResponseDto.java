package likelion_insideout.emotion.api_expense_post.model.dto;

import likelion_insideout.emotion.entity.enums.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpenseResponseDto {

    private String keyword;
    private String content;
    private Long price;
    private EmotionType emotion;

}
