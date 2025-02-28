package tonixcare.model.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tonixcare.entities.InsuranceEntity;
import tonixcare.entities.InsuredEntity;
import tonixcare.model.dto.InsuranceDTO;
import tonixcare.repository.InsuranceRepository;
import tonixcare.model.InsuranceMapper;
import tonixcare.repository.InsuredRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InsuranceService {


    List<InsuranceEntity> getAllInsurances();

    Optional<InsuranceDTO> getInsuranceById(long id);

    void updateInsurance(long insuranceId, InsuranceDTO insuranceDTO);

    void deleteInsurance(long insuranceId);

    void createInsurance(InsuranceEntity insuranceEntity);

}
