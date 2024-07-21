package likelion_insideout.emotion.api_expense_post.model.repository;

import likelion_insideout.emotion.entity.Emotion;
import likelion_insideout.emotion.entity.enums.EmotionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EmotionsRepository extends JpaRepository<Emotion, Long> {

    Optional<Emotion> findByName(EmotionType name);

}
