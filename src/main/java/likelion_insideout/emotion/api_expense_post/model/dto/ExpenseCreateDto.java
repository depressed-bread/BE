package likelion_insideout.emotion.api_expense_post.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpenseCreateDto {


    private String message;

    public ExpenseCreateDto() {
        this.message = "성공적으로 저장되었습니다.";
    }

}
