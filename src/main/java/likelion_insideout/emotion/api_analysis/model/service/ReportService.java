package likelion_insideout.emotion.api_analysis.model.service;

import likelion_insideout.emotion.api_analysis.model.dto.DayEmotionDto;
import likelion_insideout.emotion.api_analysis.model.dto.ExpenseReportDto;
import likelion_insideout.emotion.api_analysis.model.dto.ExpenseDetailDto;
import likelion_insideout.emotion.api_analysis.model.dto.ExpenseDto;
import likelion_insideout.emotion.api_analysis.model.repository.EmotionRepository;
import likelion_insideout.emotion.api_analysis.model.repository.ExpenseRepository;
import likelion_insideout.emotion.api_analysis.model.repository.UserRepository;
import likelion_insideout.emotion.entity.User;
import likelion_insideout.emotion.entity.enums.EmotionType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final EmotionRepository emotionRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ExpenseReportDto getDayExpenses(Authentication authentication, String emotionType) {
        User user = (User) userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + authentication.getName()));

        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(user.getId());

        // 오늘 날짜 구하기
        LocalDate today = LocalDate.now();

        // 감정 유형 필터링
        EmotionType filterEmotionType = null;
        if (!emotionType.equalsIgnoreCase("ALL")) {
            try {
                filterEmotionType = EmotionType.valueOf(emotionType.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid emotion type: " + emotionType);
            }
        }

        // 오늘 날짜의 지출 정보 필터링
        EmotionType finalFilterEmotionType = filterEmotionType;
        List<ExpenseDetailDto> expenseDetailDtoList = expenses.stream()
                .filter(expense -> expense.getDate().toLocalDate().isEqual(today))
                .filter(expense -> finalFilterEmotionType == null || expense.getEmotion() == finalFilterEmotionType)
                .map(expense -> new ExpenseDetailDto(
                        expense.getId(),
                        expense.getKeyword(),
                        expense.getPrice(),
                        expense.getEmotion()))
                .collect(Collectors.toList());

        ExpenseReportDto expenseReportDto = new ExpenseReportDto(Date.valueOf(today), expenseDetailDtoList);

        return expenseReportDto;
    }

    @Transactional(readOnly = true)
    public List<ExpenseReportDto> getWeekExpenses(Authentication authentication, String emotionType) {
        User user = (User) userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + authentication.getName()));

        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(user.getId());

        // 오늘 날짜 구하기
        LocalDate today = LocalDate.now();

        // 현재 주의 시작일과 종료일 구하기 (월요일부터 일요일까지)
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        List<ExpenseReportDto> expenseReportDtoList = new ArrayList<>();

        expenseReportDtoList = getExpensesByPeriod(expenses, startOfWeek, endOfWeek, emotionType);

        return expenseReportDtoList;
    }

    @Transactional(readOnly = true)
    public List<ExpenseReportDto> getMonthExpenses(Authentication authentication, String emotionType) {
        User user = (User) userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + authentication.getName()));

        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(user.getId());

        // 오늘 날짜 구하기
        LocalDate today = LocalDate.now();

        // 현재 달의 시작일과 종료일 구하기
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());

        List<ExpenseReportDto> expenseReportDtoList = getExpensesByPeriod(expenses, startOfMonth, endOfMonth, emotionType);

        return expenseReportDtoList;
    }

    @Transactional(readOnly = true)
    public List<ExpenseReportDto> getCustomExpenses(Authentication authentication, LocalDate startDate, LocalDate endDate, String emotionType) {
        User user = (User) userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + authentication.getName()));

        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(user.getId());

        List<ExpenseReportDto> expenseReportDtoList = getExpensesByPeriod(expenses, startDate, endDate, emotionType);

        return expenseReportDtoList;
    }

    @Transactional(readOnly = true)
    public List<ExpenseReportDto> getExpensesByPeriod(List<ExpenseDto> expenses, LocalDate startDate, LocalDate endDate, String emotionType) {

        // 감정 유형 필터링
        EmotionType filterEmotionType = null;
        if (!emotionType.equalsIgnoreCase("ALL")) {
            try {
                filterEmotionType = EmotionType.valueOf(emotionType.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid emotion type: " + emotionType);
            }
        }

        // 기간 내 지출 정보 조회
        List<ExpenseDto> periodExpenses = expenses.stream()
                .filter(expense -> expense.getDate().toLocalDate().isAfter(startDate.minusDays(1)) &&
                        expense.getDate().toLocalDate().isBefore(endDate.plusDays(1)))
                .collect(Collectors.toList());

        List<ExpenseReportDto> expenseReportDtoList = new ArrayList<>();

        LocalDate currentDay = startDate;

        // 오늘 날짜의 지출 정보 필터링
        EmotionType finalFilterEmotionType = filterEmotionType;

        while (!currentDay.isAfter(endDate)) {
            final LocalDate dayToProcess = currentDay;
            List<ExpenseDetailDto> expenseDetails = periodExpenses.stream()
                    .filter(expense -> expense.getDate().toLocalDate().isEqual(dayToProcess))
                    .filter(expense -> finalFilterEmotionType == null || expense.getEmotion() == finalFilterEmotionType)
                    .map(expense -> new ExpenseDetailDto(
                            expense.getId(),
                            expense.getKeyword(),
                            expense.getPrice(),
                            expense.getEmotion()))
                    .collect(Collectors.toList());
            expenseReportDtoList.add(new ExpenseReportDto(Date.valueOf(currentDay), expenseDetails));
            currentDay = currentDay.plusDays(1);
        }

        return expenseReportDtoList;
    }

    public List<DayEmotionDto> getMonthEmotion(Authentication authentication, int year, int month) {
        User user = (User) userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + authentication.getName()));

        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(user.getId());

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        List<ExpenseReportDto> expenseReportDtoList = getExpensesByPeriod(expenses, startDate, endDate, "ALL");

        List<DayEmotionDto> dayEmotionDtos = new ArrayList<>();

        for (ExpenseReportDto reportDto : expenseReportDtoList) {
            Map<EmotionType, Integer> emotionCountMap = new HashMap<>();
            Map<EmotionType, Long> emotionPriceMap = new HashMap<>();

            for (ExpenseDetailDto detail : reportDto.getExpenses()) {
                EmotionType emotionType = detail.getEmotion();
                emotionCountMap.put(emotionType, emotionCountMap.getOrDefault(emotionType, 0) + 1);
                emotionPriceMap.put(emotionType, emotionPriceMap.getOrDefault(emotionType, 0L) + detail.getPrice());
            }

            EmotionType representativeEmotion = null;
            int maxCount = 0;
            long maxPrice = 0;

            for (Map.Entry<EmotionType, Integer> entry : emotionCountMap.entrySet()) {
                EmotionType emotionType = entry.getKey();
                int count = entry.getValue();
                long price = emotionPriceMap.get(emotionType);

                if (count > maxCount || (count == maxCount && price > maxPrice)) {
                    representativeEmotion = emotionType;
                    maxCount = count;
                    maxPrice = price;
                }
            }

            dayEmotionDtos.add(new DayEmotionDto(reportDto.getDate(), representativeEmotion));
        }

        return dayEmotionDtos;
    }
}
