package likelion_insideout.emotion.entity;

import jakarta.persistence.*;
import likelion_insideout.emotion.entity.enums.EmotionType;
import likelion_insideout.emotion.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default '무표정'")
    private EmotionType emotion;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Expense> expenseList = new ArrayList<>();
}
