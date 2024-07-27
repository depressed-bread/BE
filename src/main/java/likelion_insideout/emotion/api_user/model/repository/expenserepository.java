package likelion_insideout.emotion.api_user.model.repository;

import likelion_insideout.emotion.entity.Expense;
import likelion_insideout.emotion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface expenserepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);  // 특정 사용자 ID에 따라 모든 지출을 반환

    List<Expense> findByUser(User user);
}
