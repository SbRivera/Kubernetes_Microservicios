package publicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import publicaciones.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByEmail(String email);
    boolean existsByOrcid(String orcid);

}
