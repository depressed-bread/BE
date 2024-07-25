package likelion_insideout.emotion.api_analysis.model.repository;

import likelion_insideout.emotion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Object> findByEmail(String name);
}
