package likelion_insideout.emotion.api_analysis.model.service;

import likelion_insideout.emotion.api_analysis.model.dto.ExpenseDto;
import likelion_insideout.emotion.api_analysis.model.repository.EmotionRepository;
import likelion_insideout.emotion.api_analysis.model.repository.ExpenseRepository;
import likelion_insideout.emotion.api_analysis.model.repository.UserRepository;
import likelion_insideout.emotion.api_user.model.repository.apiUserRepository;
import likelion_insideout.emotion.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatService {
    private final EmotionRepository emotionRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public Long getDayPrice(Authentication authentication) {
        User user = (User) userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + authentication.getName()));

        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(user.getId());

        // 오늘 날짜 구하기
        LocalDate today = LocalDate.now();

        // 오늘 날짜의 지출 정보 필터링
        List<ExpenseDto> currentExpenses = expenses.stream()
                .filter(expense -> expense.getDate().toLocalDate().isEqual(today))
                .collect(Collectors.toList());

        Long totalPrice = 0L;

        for (ExpenseDto expenseDto : currentExpenses) {
            totalPrice += expenseDto.getPrice();
        }

        return totalPrice;
    }

    public Long getWeekPrice(Authentication authentication) {
        User user = (User) userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + authentication.getName()));

        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(user.getId());

        // 오늘 날짜 구하기
        LocalDate today = LocalDate.now();

        // 현재 주의 시작일과 종료일 구하기 (월요일부터 일요일까지)
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        return getPriceByPeriod(expenses, startOfWeek, endOfWeek);
    }

    public Long getMonthPrice(Authentication authentication) {
        User user = (User) userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + authentication.getName()));

        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(user.getId());

        // 오늘 날짜 구하기
        LocalDate today = LocalDate.now();

        // 현재 달의 시작일과 종료일 구하기
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());

        return getPriceByPeriod(expenses, startOfMonth, endOfMonth);
    }

    public Long getCustomPrice(Authentication authentication, LocalDate startDate, LocalDate endDate) {
        User user = (User) userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + authentication.getName()));

        // 모든 지출 정보 조회
        List<ExpenseDto> expenses = expenseRepository.findAllByUserId(user.getId());

        return getPriceByPeriod(expenses, startDate, endDate);
    }

    public Long getPriceByPeriod(List<ExpenseDto> expenses, LocalDate startDate, LocalDate endDate){
        Long totalPrice = 0L;

        // 기간 내 지출 정보 조회
        List<ExpenseDto> periodExpenses = expenses.stream()
                .filter(expense -> expense.getDate().toLocalDate().isAfter(startDate.minusDays(1)) &&
                        expense.getDate().toLocalDate().isBefore(endDate.plusDays(1)))
                .collect(Collectors.toList());

        for (ExpenseDto expenseDto : periodExpenses) {
            totalPrice += expenseDto.getPrice();
        }

        return totalPrice;
    }
}
