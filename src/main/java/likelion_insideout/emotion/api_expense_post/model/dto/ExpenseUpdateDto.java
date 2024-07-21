package likelion_insideout.emotion.api_expense_post.model.dto;

import lombok.Data;

@Data
public class ExpenseUpdateDto {

    private String message;

    public ExpenseUpdateDto() {
        this.message = "성공적으로 수정되었습니다.";
    }

}
