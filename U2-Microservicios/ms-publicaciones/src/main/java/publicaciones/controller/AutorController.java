package publicaciones.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import publicaciones.dto.AutorDto;
import publicaciones.dto.ResponseDto;
import publicaciones.service.AutorService;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    // Crear autor - POST
    @PostMapping
    public ResponseDto crearAutor(@RequestBody AutorDto dto) {
        return autorService.crearAutor(dto);
    }

    // Obtener todos los autores - GET
    @GetMapping
    public List<ResponseDto> obtenerAutores() {
        return autorService.listAutores();
    }

    // Buscar autor por ID - GET
    @GetMapping("/{id}")
    public ResponseDto buscarPorId(@PathVariable Long id) {
        return autorService.autorPorId(id);
    }

    // Actualizar autor por ID - PUT
    @PutMapping("/{id}")
    public ResponseDto actualizarAutor(@PathVariable Long id, @RequestBody AutorDto dto) {
        return autorService.actualizarAutor(id, dto);
    }

    // Eliminar autor por ID - DELETE
    @DeleteMapping("/{id}")
    public ResponseDto eliminarAutor(@PathVariable Long id) {
        return autorService.eliminarAutor(id);
    }

}
