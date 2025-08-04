package publicaciones.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="author")
@Getter
@Setter

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String nombre;

    @Column(name = "lastname", nullable = false)
    private String apellido;

    private String nacionalidad;
    private String institucion;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "orcid", nullable = false, length = 50, unique = true)
    private String orcid;

    @OneToMany(mappedBy = "autor")
    @JsonIgnore
    private List<Libro> libros;

    @OneToMany(mappedBy = "autor")
    @JsonIgnore
    private List<Paper> papers;
}