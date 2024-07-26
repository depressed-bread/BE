package likelion_insideout.emotion.entity;

import jakarta.persistence.*;
import likelion_insideout.emotion.entity.enums.EmotionType;
import likelion_insideout.emotion.entity.enums.Role;
import lombok.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    private String name;

    private String email;

    private String password;

    //@Getter(AccessLevel.NONE)
    //@Setter(AccessLevel.NONE)
    @Setter
    private String phone;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    private EmotionType emotion;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Expense> expenseList = new ArrayList<>();

    public void updatePassword(String encodePw) {
        this.password = encodePw;
    }

}
