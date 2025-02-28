package tonixcare.model;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import tonixcare.entities.InsuredEntity;
import tonixcare.model.dto.InsuredDTO;

@Mapper(componentModel = "spring")
public interface InsuredMapper {

    InsuredDTO toDTO(InsuredEntity source);

    InsuredEntity toEntity(InsuredDTO source);

    // Tady měníme metody na vracení objektu, místo void
    InsuredDTO updateInsuredDTO(InsuredEntity source,@MappingTarget InsuredDTO target);

    InsuredEntity updateInsuredEntity(InsuredDTO source, @MappingTarget InsuredEntity target);
}
