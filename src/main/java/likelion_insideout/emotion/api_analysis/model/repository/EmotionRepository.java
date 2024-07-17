package likelion_insideout.emotion.api_analysis.model.repository;

import likelion_insideout.emotion.entity.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {
}
