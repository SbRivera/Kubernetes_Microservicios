package publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AutorDto {
    private String nombre;
    private String apellido;
    private String email;
    private String nacionalidad;
    private String institucion;
    private String orcid;
}
