package publicaciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.PaperDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Autor;
import publicaciones.model.Paper;
import publicaciones.repository.AutorRepository;
import publicaciones.repository.PaperRepository;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CatalogoProducer catalogoProducer;

    @Autowired
    private NotificacionProducer notificacionProducer;

    public ResponseDto crearPaper(PaperDto dto) {
        if (dto.getAutorId() == null) {
            throw new RuntimeException("Debe especificar el ID del autor.");
        }
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("El título es obligatorio.");
        }

        if (dto.getIsbn() != null && paperRepository.existsByIsbn(dto.getIsbn())) {
            throw new RuntimeException("Ya existe un paper con este ISBN.");
        }

        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("No existe el autor con ID: " + dto.getAutorId()));

        Paper paper = new Paper();
        paper.setAutor(autor);
        paper.setTitulo(dto.getTitulo());
        paper.setResumen(dto.getResumen());
        paper.setAnioPublicacion(dto.getAnioPublicacion());
        paper.setEditorial(dto.getEditorial());
        paper.setIsbn(dto.getIsbn());
        paper.setOrcid(dto.getOrcid());
        paper.setFechaPublicacion(Date.valueOf(dto.getFechaPublicacion()));
        paper.setRevista(dto.getRevista());
        paper.setAreaInvestigacion(dto.getAreaInvestigacion());

        notificacionProducer.enviarNotificacion("Se registró el artículo: " + paper.getTitulo(), "Paper");

        catalogoProducer.enviarArticuloCatalogo(dto);

        return new ResponseDto("Paper registrado exitosamente", paperRepository.save(paper));
    }

    public List<ResponseDto> listarPapers() {
        return ((List<Paper>) paperRepository.findAll()).stream()
                .map(p -> new ResponseDto("Paper: " + p.getTitulo(), p))
                .collect(Collectors.toList());
    }

    public ResponseDto obtenerPaperPorId(Long id) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el paper con id: " + id));
        return new ResponseDto("Paper encontrado", paper);
    }

    public ResponseDto actualizarPaper(Long id, PaperDto dto) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el paper con ID: " + id));

        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("El título es obligatorio.");
        }

        if (dto.getAutorId() == null) {
            throw new RuntimeException("Debe especificar el ID del autor.");
        }

        if (dto.getIsbn() != null && !dto.getIsbn().equals(paper.getIsbn())
                && paperRepository.existsByIsbn(dto.getIsbn())) {
            throw new RuntimeException("Ya existe otro paper con este ISBN.");
        }

        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("No existe el autor con ID: " + dto.getAutorId()));

        paper.setAutor(autor);
        paper.setTitulo(dto.getTitulo());
        paper.setResumen(dto.getResumen());
        paper.setAnioPublicacion(dto.getAnioPublicacion());
        paper.setEditorial(dto.getEditorial());
        paper.setIsbn(dto.getIsbn());
        paper.setOrcid(dto.getOrcid());
        paper.setFechaPublicacion(Date.valueOf(dto.getFechaPublicacion()));
        paper.setRevista(dto.getRevista());
        paper.setAreaInvestigacion(dto.getAreaInvestigacion());

        return new ResponseDto("Paper actualizado exitosamente", paperRepository.save(paper));
    }

    public ResponseDto eliminarPaper(Long id) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el paper con id: " + id));
        paperRepository.delete(paper);
        return new ResponseDto("Paper eliminado exitosamente", null);
    }
}
