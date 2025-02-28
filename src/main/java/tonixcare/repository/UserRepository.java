package tonixcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tonixcare.entities.UserEntity;

// Repozitář pro práci s entitou UserEntity
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // Vlastní metoda pro hledání uživatele podle emailu
    UserEntity findByEmail(String email);
}
