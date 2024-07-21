package likelion_insideout.emotion.api_expense_post.model.dto;

import lombok.Data;

@Data
public class ExpenseDeleteDto {

    private String message;

    public ExpenseDeleteDto() {
        this.message = "성공적으로 삭제되었습니다.";
    }

}
