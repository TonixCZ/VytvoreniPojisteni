package tonixcare.model.services;

import tonixcare.model.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {

    void create(ArticleDTO article);

    List<ArticleDTO> getAll();

    ArticleDTO getById(long articleId);

    void edit(ArticleDTO article);

    void remove(long articleId);

}

