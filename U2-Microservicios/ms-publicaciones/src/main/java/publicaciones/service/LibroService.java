package publicaciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.LibroDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Autor;
import publicaciones.model.Libro;
import publicaciones.repository.AutorRepository;
import publicaciones.repository.LibroRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CatalogoProducer catalogoProducer;

    @Autowired
    private NotificacionProducer notificacionProducer;

    public ResponseDto crearLibro(LibroDto dto){
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("El título es obligatorio.");
        }

        if (dto.getIsbn() == null || dto.getIsbn().trim().isEmpty()) {
            throw new RuntimeException("El ISBN es obligatorio.");
        }

        if (dto.getAutorId() == null) {
            throw new RuntimeException("Debe especificar el autor.");
        }

        if (libroRepository.existsByIsbn(dto.getIsbn())) {
            throw new RuntimeException("Ya existe un libro con este ISBN.");
        }

        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("No existe el autor con ID: " + dto.getAutorId()));

        Libro libro = new Libro();
        libro.setAutor(autor);
        libro.setTitulo(dto.getTitulo());
        libro.setGenero(dto.getGenero());
        libro.setEditorial(dto.getEditorial());
        libro.setIsbn(dto.getIsbn());
        libro.setNumeroPaginas(dto.getNumeroPaginas());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setResumen(dto.getResumen());

        notificacionProducer.enviarNotificacion("Se registró el libro: " + libro.getTitulo(), "Libro");

        catalogoProducer.enviarLibroCatalogo(dto);

        return new ResponseDto("Libro registrado exitosamente", libroRepository.save(libro));

    }

    public List<ResponseDto> listarLibros() {
        return libroRepository.findAll().stream()
                .map(libro -> new ResponseDto("Libro: " + libro.getTitulo(), libro))
                .collect(Collectors.toList());
    }

    public ResponseDto obtenerLibroPorId(int id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el libro con id: " + id));
        return new ResponseDto("Libro con ID: " + libro.getId(), libro);
    }

    public ResponseDto actualizarLibro(int id, LibroDto dto) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el libro con ID: " + id));

        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("El título es obligatorio.");
        }

        if (dto.getIsbn() == null || dto.getIsbn().trim().isEmpty()) {
            throw new RuntimeException("El ISBN es obligatorio.");
        }

        if (dto.getAutorId() == null) {
            throw new RuntimeException("Debe especificar el autor.");
        }

        // Validación de duplicados si el ISBN cambia
        if (!dto.getIsbn().equals(libro.getIsbn()) && libroRepository.existsByIsbn(dto.getIsbn())) {
            throw new RuntimeException("Ya existe otro libro con este ISBN.");
        }

        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("No existe el autor con ID: " + dto.getAutorId()));

        libro.setAutor(autor);
        libro.setTitulo(dto.getTitulo());
        libro.setGenero(dto.getGenero());
        libro.setIsbn(dto.getIsbn());
        libro.setNumeroPaginas(dto.getNumeroPaginas());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setResumen(dto.getResumen());

        return new ResponseDto("Libro actualizado exitosamente", libroRepository.save(libro));
    }

    public ResponseDto eliminarLibro(int id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el libro con id: " + id));
        libroRepository.delete(libro);
        return new ResponseDto("Libro eliminado exitosamente", null);
    }
}
