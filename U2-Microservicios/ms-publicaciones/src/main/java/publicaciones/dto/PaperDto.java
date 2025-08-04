package publicaciones.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperDto {
    private String titulo;
    private int anioPublicacion;
    private String editorial;
    private String isbn;
    private String resumen;

    private String orcid;
    private LocalDate fechaPublicacion;
    private String revista;
    private String areaInvestigacion;

    private Long autorId;
}
