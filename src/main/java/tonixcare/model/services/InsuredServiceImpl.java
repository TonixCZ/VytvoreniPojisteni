package tonixcare.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tonixcare.entities.InsuredEntity;
import tonixcare.model.dto.InsuredDTO;
import tonixcare.model.InsuredMapper;
import tonixcare.model.exceptions.InsuredNotFoundException;
import tonixcare.repository.InsuredRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class InsuredServiceImpl implements InsuredService {

    @Autowired
    private InsuredRepository insuredRepository;

    @Autowired
    private InsuredMapper insuredMapper;

    @Override
    public void create(InsuredDTO insured) {
        InsuredEntity newInsured = insuredMapper.toEntity(insured);
        insuredRepository.save(newInsured);
    }

    @Override
    public List<InsuredDTO> getAll() {
        return StreamSupport.stream(insuredRepository.findAll().spliterator(), false)
                .map(i -> insuredMapper.toDTO(i))
                .toList();
    }

    @Override
    public InsuredDTO getById(long insuredId) {
        InsuredEntity fetchedInsured = getInsuredOrThrow(insuredId);

        return insuredMapper.toDTO(fetchedInsured);
    }

    @Override
    public void edit(InsuredDTO insured) {
        InsuredEntity fetchedInsured = getInsuredOrThrow(insured.getInsuredId());

        insuredMapper.updateInsuredEntity(insured, fetchedInsured);
        insuredRepository.save(fetchedInsured);
    }

    @Override
    public void remove(long insuredId) {
        InsuredEntity fetchedEntity = getInsuredOrThrow(insuredId);
        insuredRepository.delete(fetchedEntity);
    }

    private InsuredEntity getInsuredOrThrow(long insuredId) {
        return insuredRepository
                .findById(insuredId)
                .orElseThrow(InsuredNotFoundException::new);
    }
}