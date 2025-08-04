package publicaciones.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
public class Paper extends Publicacion {
    private String orcid;
    private Date fechaPublicacion;
    private String revista;
    private String areaInvestigacion;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    @JsonIgnoreProperties({"papers"})
    private Autor autor;
}
