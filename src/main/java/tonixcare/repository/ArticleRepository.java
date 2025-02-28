package tonixcare.repository;


import org.springframework.data.repository.CrudRepository;
import tonixcare.entities.ArticleEntity;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {
}