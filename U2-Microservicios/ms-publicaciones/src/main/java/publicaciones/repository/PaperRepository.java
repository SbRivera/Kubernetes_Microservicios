package publicaciones.repository;

import org.springframework.data.repository.CrudRepository;
import publicaciones.model.Paper;

public interface PaperRepository extends CrudRepository<Paper, Long> {
    boolean existsByIsbn(String isbn);
}
