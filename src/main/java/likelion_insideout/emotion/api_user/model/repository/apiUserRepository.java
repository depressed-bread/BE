package likelion_insideout.emotion.api_user.model.repository;

import likelion_insideout.emotion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface apiUserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email); //이메일로 사용자 정보 가져옴
    Optional<User> findByNameAndPhone(String name, String phone);

}