package likelion_insideout.emotion.api_analysis.model.repository;

import likelion_insideout.emotion.api_analysis.model.dto.ExpenseDto;
import likelion_insideout.emotion.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT new likelion_insideout.emotion.api_analysis.model.dto.ExpenseDto(e.id, e.date, e.emotion.name, e.price, e.keyword) " +
            "FROM Expense e " +
            "WHERE e.user.id = :userId ")
    List<ExpenseDto> findAllByUserId(@Param("userId") Long userId);

}
