package tonixcare.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tonixcare.entities.InsuredEntity;
import tonixcare.entities.UserEntity;

import java.util.Optional;

public interface InsuredRepository extends JpaRepository<InsuredEntity, Long> {
    Optional<InsuredEntity> findById(Long insuredId);

    Optional<InsuredEntity> findByEmail(String email);
}