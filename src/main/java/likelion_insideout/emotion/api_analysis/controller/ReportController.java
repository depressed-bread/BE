package likelion_insideout.emotion.api_analysis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion_insideout.emotion.api_analysis.model.dto.DayEmotionDto;
import likelion_insideout.emotion.api_analysis.model.dto.ExpenseReportDto;
import likelion_insideout.emotion.api_analysis.model.service.ReportService;
import likelion_insideout.emotion.entity.enums.EmotionType;
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
    public ExpenseReportDto getDayExpenses(
            @RequestParam("id") Long id,
            @RequestParam(value = "emotionType", defaultValue = "ALL") String emotionType) {
        return reportService.getDayExpenses(id, emotionType);
    }

    @GetMapping("/week")
    @Operation(summary = "주간 지출 내역")
    public List<ExpenseReportDto> getWeekExpenses(
            @RequestParam("id") Long id,
            @RequestParam(value = "emotionType", defaultValue = "ALL") String emotionType) {
        return reportService.getWeekExpenses(id, emotionType);
    }

    @GetMapping("/month")
    @Operation(summary = "월간 지출 내역")
    public List<ExpenseReportDto> getMonthExpenses(
            @RequestParam("id") Long id,
            @RequestParam(value = "emotionType", defaultValue = "ALL") String emotionType) {
        return reportService.getMonthExpenses(id, emotionType);
    }

    @GetMapping("/custom")
    @Operation(summary = "날짜지정 지출 내역")
    public List<ExpenseReportDto> getCustomExpenses(
            @RequestParam("id") Long id,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(value = "emotionType", defaultValue = "ALL") String emotionType) {
        return reportService.getCustomExpenses(id, startDate, endDate, emotionType);
    }

    @GetMapping("/emotion")
    @Operation(summary = "월별 감정 조회")
    public List<DayEmotionDto> getMonthEmotion(
            @RequestParam("id") Long id,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        return reportService.getMonthEmotion(id, year, month);
    }
}