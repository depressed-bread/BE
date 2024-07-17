package likelion_insideout.emotion.api_analysis.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ExpenseReportDto {
    private Date date;
    private List<ExpenseDetailDto> expenses;
}
