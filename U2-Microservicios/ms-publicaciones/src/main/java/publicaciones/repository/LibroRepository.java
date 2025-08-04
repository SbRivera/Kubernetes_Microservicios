package publicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import publicaciones.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    boolean existsByIsbn(String isbn);
}
