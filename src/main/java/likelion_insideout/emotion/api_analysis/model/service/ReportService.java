package likelion_insideout.emotion.api_analysis.model.service;

import likelion_insideout.emotion.api_analysis.model.dto.ExpenseReportDto;
import likelion_insideout.emotion.api_analysis.model.dto.ExpenseDetailDto;
import likelion_insideout.emotion.api_analysis.model.dto.ExpenseDto;
import likelion_insideout.emotion.api_analysis.model.repository.EmotionRepository;
import likelion_insideout.emotion.api_analysis.model.repository.ExpenseRepository;
import likelion_insideout.emotion.api_analysis.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final EmotionRepository emotionRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ExpenseReportDto getDayExpenses(Long id) {
        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(id);

        // 오늘 날짜 구하기
        LocalDate today = LocalDate.now();

        // 오늘 날짜의 지출 정보 필터링
        List<ExpenseDetailDto> expenseDetailDtoList = expenses.stream()
                .filter(expense -> expense.getDate().toLocalDate().isEqual(today))
                .map(expense -> new ExpenseDetailDto(
                        expense.getKeyword(),
                        expense.getPrice(),
                        expense.getEmotion()))
                .collect(Collectors.toList());

        ExpenseReportDto expenseReportDto = new ExpenseReportDto(Date.valueOf(today), expenseDetailDtoList);

        return expenseReportDto;
    }

    @Transactional(readOnly = true)
    public List<ExpenseReportDto> getWeekExpenses(Long id) {
        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(id);

        // 오늘 날짜 구하기
        LocalDate today = LocalDate.now();

        // 현재 주의 시작일과 종료일 구하기 (월요일부터 일요일까지)
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        List<ExpenseReportDto> expenseReportDtoList = new ArrayList<>();

        expenseReportDtoList = getExpensesByPeriod(expenses, startOfWeek, endOfWeek);

        return expenseReportDtoList;
    }

    @Transactional(readOnly = true)
    public List<ExpenseReportDto> getMonthExpenses(Long id) {
        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(id);

        // 오늘 날짜 구하기
        LocalDate today = LocalDate.now();

        // 현재 달의 시작일과 종료일 구하기
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());

        List<ExpenseReportDto> expenseReportDtoList = getExpensesByPeriod(expenses, startOfMonth, endOfMonth);

        return expenseReportDtoList;
    }

    @Transactional(readOnly = true)
    public List<ExpenseReportDto> getCustomExpenses(Long id, LocalDate startDate, LocalDate endDate) {
        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(id);

        List<ExpenseReportDto> expenseReportDtoList = getExpensesByPeriod(expenses, startDate, endDate);

        return expenseReportDtoList;
    }

    @Transactional(readOnly = true)
    public List<ExpenseReportDto> getExpensesByPeriod(List<ExpenseDto> expenses, LocalDate startDate, LocalDate endDate) {

        // 기간 내 지출 정보 조회
        List<ExpenseDto> periodExpenses = expenses.stream()
                .filter(expense -> expense.getDate().toLocalDate().isAfter(startDate.minusDays(1)) &&
                        expense.getDate().toLocalDate().isBefore(endDate.plusDays(1)))
                .collect(Collectors.toList());

        List<ExpenseReportDto> expenseReportDtoList = new ArrayList<>();

        LocalDate currentDay = startDate;

        while (!currentDay.isAfter(endDate)) {
            final LocalDate dayToProcess = currentDay;
            List<ExpenseDetailDto> expenseDetails = periodExpenses.stream()
                    .filter(expense -> expense.getDate().toLocalDate().isEqual(dayToProcess))
                    .map(expense -> new ExpenseDetailDto(
                            expense.getKeyword(),
                            expense.getPrice(),
                            expense.getEmotion()))
                    .collect(Collectors.toList());
            expenseReportDtoList.add(new ExpenseReportDto(Date.valueOf(currentDay), expenseDetails));
            currentDay = currentDay.plusDays(1);
        }

        return expenseReportDtoList;
    }
}
