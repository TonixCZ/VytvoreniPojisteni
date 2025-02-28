package tonixcare.model.services;
import tonixcare.model.dto.InsuredDTO;

import java.util.List;

public interface InsuredService {


    void create(InsuredDTO insured);

    List<InsuredDTO> getAll();

     InsuredDTO getById(long insuredId);

    void edit(InsuredDTO insured);

    void remove(long insuredId);

}


