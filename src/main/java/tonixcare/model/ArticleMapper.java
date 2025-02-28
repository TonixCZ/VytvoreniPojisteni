package tonixcare.model;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import tonixcare.entities.ArticleEntity;
import tonixcare.model.dto.ArticleDTO;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleEntity toEntity(ArticleDTO source);

    ArticleDTO toDTO(ArticleEntity source);

    void updateArticleDTO(ArticleDTO source, @MappingTarget ArticleDTO target);

    void updateArticleEntity(ArticleDTO source, @MappingTarget ArticleEntity target);

}