package publicaciones.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Libro extends Publicacion {
    private int numeroPaginas;
    private String genero;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    @JsonIgnoreProperties({"libros"})
    private Autor autor;
}
