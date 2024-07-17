package likelion_insideout.emotion.entity;

import jakarta.persistence.*;
import likelion_insideout.emotion.entity.enums.EmotionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "emotion")
@Getter
@Setter
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmotionType name;

    @OneToMany(mappedBy = "emotion", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Expense> expenseList = new ArrayList<>();
}
