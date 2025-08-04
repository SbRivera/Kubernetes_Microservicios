package publicaciones.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import publicaciones.dto.LibroDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Libro;
import publicaciones.service.LibroService;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/libros")
public class LibroController {
    @Autowired
    private LibroService libroService;

    @PostMapping
    public ResponseDto createLibro(@RequestBody LibroDto dto) {
        return libroService.crearLibro(dto);
    }

    @GetMapping
    public List<ResponseDto> obtenerLibros() {
        return libroService.listarLibros();
    }

    @GetMapping("/{id}")
    public ResponseDto obtenerLibroPorId(@PathVariable int id) {
        return libroService.obtenerLibroPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarLibro(@PathVariable int id, @RequestBody LibroDto dto) {
        return libroService.actualizarLibro(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto eliminarLibro(@PathVariable int id) {
        return libroService.eliminarLibro(id);
    }
}

