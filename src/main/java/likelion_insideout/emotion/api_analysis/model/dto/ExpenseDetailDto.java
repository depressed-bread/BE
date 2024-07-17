package likelion_insideout.emotion.api_analysis.model.dto;

import likelion_insideout.emotion.entity.enums.EmotionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpenseDetailDto {
    private String keyword;
    private Long price;
    private EmotionType emotion;
}

