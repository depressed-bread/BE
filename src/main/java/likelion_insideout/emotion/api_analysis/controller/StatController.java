package likelion_insideout.emotion.api_analysis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion_insideout.emotion.api_analysis.model.service.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/stat")
@RequiredArgsConstructor
@Tag(name = "통계 api", description = "통계 관련 api")
public class StatController {
    private final StatService statService;

    @GetMapping("/day")
    @Operation(summary = "일일 소비 통계")
    public Long getDayPrice(@RequestParam("id") Long id) {
        return statService.getDayPrice(id);
    }

    @GetMapping("/week")
    @Operation(summary = "주간 소비 통계")
    public Long getWeekPrice(@RequestParam("id") Long id) {
        return statService.getWeekPrice(id);
    }

    @GetMapping("/month")
    @Operation(summary = "월간 소비 통계")
    public Long getMonthPrice(@RequestParam("id") Long id) {
        return statService.getMonthPrice(id);
    }

    @GetMapping("/custom")
    @Operation(summary = "날짜지정 소비 통계")
    public Long getCustomPrice(
            @RequestParam("id") Long id,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return statService.getCustomPrice(id, startDate, endDate);
    }
}
