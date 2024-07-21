package likelion_insideout.emotion.api_expense_post.model.repository;

import likelion_insideout.emotion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
}
