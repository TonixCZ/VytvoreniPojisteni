package tonixcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonixcare.entities.InsuranceEntity;
import tonixcare.entities.UserEntity;

import java.util.List;

public interface InsuranceRepository extends JpaRepository<InsuranceEntity, Long> {
}