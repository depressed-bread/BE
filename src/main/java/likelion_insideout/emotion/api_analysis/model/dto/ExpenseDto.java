package likelion_insideout.emotion.api_analysis.model.dto;

import likelion_insideout.emotion.entity.enums.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class ExpenseDto {
    private Date date;
    private EmotionType emotion;
    private Long price;
    private String keyword;
}
