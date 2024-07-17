package likelion_insideout.emotion.api_analysis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion_insideout.emotion.api_analysis.model.dto.ExpenseReportDto;
import likelion_insideout.emotion.api_analysis.model.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
@Tag(name = "분석 api", description = "지출 내역 분석 api")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/day")
    @Operation(summary = "일간 지출 내역")
    public ExpenseReportDto getDayExpenses(@RequestParam Long id) {
        return reportService.getDayExpenses(id);
    }

    @GetMapping("/week")
    @Operation(summary = "주간 지출 내역")
    public List<ExpenseReportDto> getWeekExpenses(@RequestParam Long id) {
        return reportService.getWeekExpenses(id);
    }

    @GetMapping("/month")
    @Operation(summary = "월간 지출 내역")
    public List<ExpenseReportDto> getMonthExpenses(@RequestParam Long id) {
        return reportService.getMonthExpenses(id);
    }

    @GetMapping("/custom")
    @Operation(summary = "날짜지정 지출 내역")
    public List<ExpenseReportDto> getCustomExpenses(@RequestParam Long id,
                                            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return reportService.getCustomExpenses(id, startDate, endDate);
    }
}