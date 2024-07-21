package likelion_insideout.emotion.entity;

import jakarta.persistence.*;
import likelion_insideout.emotion.api_expense_post.model.dto.ExpenseRequestDto;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "expense")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String keyword;

    private Long price;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emotion_id", nullable = false)
    private Emotion emotion;

    public void update(ExpenseRequestDto expenseRequestDto, Emotion emotion) {

        this.keyword = expenseRequestDto.getKeyword();
        this.content = expenseRequestDto.getContent();
        this.price = expenseRequestDto.getPrice();
        this.date = expenseRequestDto.getDate();
        this.emotion = emotion;

    }
}
