package likelion_insideout.emotion.entity;

import jakarta.persistence.*;
import likelion_insideout.emotion.entity.enums.EmotionType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "emotion")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmotionType name;

    /*@Getter
    @Enumerated(EnumType.STRING)
    private EmotionType type;*/

    @OneToMany(mappedBy = "emotion", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Expense> expenseList = new ArrayList<>();

    @OneToMany(mappedBy = "emotion", fetch = FetchType.LAZY)
    private List<Expense> expenses;

    @Builder
    public Emotion(Long id, EmotionType name, List<Expense> expenses) {
        this.id = id;
        this.name = name;
        this.expenses = expenses;
    }

    public EmotionType getType() {
        return name;  // 이 메소드가 EmotionType을 반환합니다.
    }
}
