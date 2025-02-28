package tonixcare.model;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import tonixcare.entities.InsuranceEntity;
import tonixcare.model.dto.InsuranceDTO;

@Mapper(componentModel = "spring")
public interface InsuranceMapper {

    // Převod z entity na DTO
    InsuranceDTO toDTO(InsuranceEntity source);

    // Převod z DTO na entity
    InsuranceEntity toEntity(InsuranceDTO source);

    // Aktualizace DTO
    void updateInsuranceDTO(InsuranceEntity source, @MappingTarget InsuranceDTO target);

    // Aktualizace entity
    void updateInsuranceEntity(InsuranceDTO source, @MappingTarget InsuranceEntity target);
}

