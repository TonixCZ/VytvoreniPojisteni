package tonixcare.model.services;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tonixcare.entities.InsuranceEntity;
import tonixcare.entities.InsuredEntity;
import tonixcare.model.InsuranceMapper;
import tonixcare.model.dto.InsuranceDTO;
import tonixcare.repository.InsuranceRepository;
import tonixcare.repository.InsuredRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InsuranceServiceImpl implements InsuranceService {

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private InsuranceMapper insuranceMapper;

    @Autowired
    private InsuredRepository insuredRepository;

    @Override
    public List<InsuranceEntity> getAllInsurances() {
        return insuranceRepository.findAll();
    }

    @Override
    public Optional<InsuranceDTO> getInsuranceById(long id) {
        return insuranceRepository.findById(id).map(insuranceMapper::toDTO);
    }

    @Override
    @Transactional
    public void updateInsurance(long insuranceId, InsuranceDTO insuranceDTO) {
        InsuranceEntity existingInsurance = insuranceRepository.findById(insuranceId)
                .orElseThrow(() -> new EntityNotFoundException("Pojištění nenalezeno."));

        existingInsurance.setInsuranceType(insuranceDTO.getInsuranceType());
        existingInsurance.setInsuredAmount(insuranceDTO.getInsuredAmount());
        existingInsurance.setInsuredAddress(insuranceDTO.getInsuredAddress());

        insuranceRepository.save(existingInsurance);
    }

    @Override
    public void deleteInsurance(long insuranceId) {
        insuranceRepository.deleteById(insuranceId);
    }

    @Override
    public void createInsurance(InsuranceEntity insuranceEntity) {
        Long insuredId = insuranceEntity.getInsured().getInsuredId();
        InsuredEntity insuredEntity = insuredRepository.findById(insuredId)
                .orElseThrow(() -> new IllegalArgumentException("Pojištěnec s ID " + insuredId + " nenalezen."));

        insuranceEntity.setInsured(insuredEntity);
        insuranceEntity.setCreatedAt(LocalDateTime.now());

        insuranceRepository.save(insuranceEntity);
    }
}
