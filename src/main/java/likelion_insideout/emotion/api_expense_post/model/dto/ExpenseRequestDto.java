package likelion_insideout.emotion.api_expense_post.model.dto;

import likelion_insideout.emotion.entity.enums.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class ExpenseRequestDto {

    private String keyword;
    private Long price;
    private Date date;
    private String content;
    private EmotionType emotionType;

}
