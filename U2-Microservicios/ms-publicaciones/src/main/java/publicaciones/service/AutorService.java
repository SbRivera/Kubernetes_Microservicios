package publicaciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import publicaciones.dto.AutorDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Autor;
import publicaciones.repository.AutorRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer producer;

    public ResponseDto crearAutor(AutorDto dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del autor es obligatorio.");
        }

        if (dto.getApellido() == null || dto.getApellido().trim().isEmpty()) {
            throw new RuntimeException("El apellido del autor es obligatorio.");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new RuntimeException("El email del autor es obligatorio.");
        }

        if (!dto.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new RuntimeException("El email ingresado no es válido.");
        }

        if (dto.getOrcid() == null || dto.getOrcid().trim().isEmpty()) {
            throw new RuntimeException("El ORCID del autor es obligatorio.");
        }

        if (autorRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe un autor con este email.");
        }

        if (autorRepository.existsByOrcid(dto.getOrcid())) {
            throw new RuntimeException("Ya existe un autor con este ORCID.");
        }

        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());
        autor.setApellido(dto.getApellido());
        autor.setEmail(dto.getEmail());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setInstitucion(dto.getInstitucion());
        autor.setOrcid(dto.getOrcid());

        Autor saved = autorRepository.save(autor);

        //Enviar notificación
        producer.enviarNotificacion("Autor: " + saved.getNombre() + " ha sido registrado", "Autor");

        //Retornar respuesta
        return new ResponseDto("Autor registrado exitosamente", autorRepository.save(autor));
    }

    public List<ResponseDto> listAutores(){
        return autorRepository.findAll().stream()
                .map(autor -> new ResponseDto("Autor: " + autor.getApellido(), autor))
                .collect(Collectors.toList());
    }
    public ResponseDto autorPorId(Long id) {
        Autor autor = autorRepository.findById(id).orElseThrow(()-> new RuntimeException("No existe el autor con id:" + id));
        return new ResponseDto("Autor con id: " + autor.getId(), autor);
    }

    public ResponseDto actualizarAutor(Long id, AutorDto dto) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el autor con id: " + id));

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del autor es obligatorio.");
        }

        if (dto.getApellido() == null || dto.getApellido().trim().isEmpty()) {
            throw new RuntimeException("El apellido del autor es obligatorio.");
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new RuntimeException("El email del autor es obligatorio.");
        }

        if (!dto.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new RuntimeException("El email ingresado no es válido.");
        }

        if (dto.getOrcid() == null || dto.getOrcid().trim().isEmpty()) {
            throw new RuntimeException("El ORCID del autor es obligatorio.");
        }

        // Validación de duplicados en update (si cambia el email o el orcid)
        if (!dto.getEmail().equals(autor.getEmail()) && autorRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe otro autor con este email.");
        }

        if (!dto.getOrcid().equals(autor.getOrcid()) && autorRepository.existsByOrcid(dto.getOrcid())) {
            throw new RuntimeException("Ya existe otro autor con este ORCID.");
        }
        autor.setNombre(dto.getNombre());
        autor.setApellido(dto.getApellido());
        autor.setEmail(dto.getEmail());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setInstitucion(dto.getInstitucion());
        autor.setOrcid(dto.getOrcid());
        return new ResponseDto("Autor actualizado exitosamente", autorRepository.save(autor));
    }

    public ResponseDto eliminarAutor(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el autor con id: " + id));

        autorRepository.delete(autor);
        return new ResponseDto("Autor eliminado exitosamente", null);
    }

}
