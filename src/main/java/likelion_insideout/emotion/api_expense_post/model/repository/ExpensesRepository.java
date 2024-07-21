package likelion_insideout.emotion.api_expense_post.model.repository;

import likelion_insideout.emotion.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpensesRepository extends JpaRepository<Expense, Long> {
}
