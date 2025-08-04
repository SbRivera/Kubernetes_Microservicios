package publicaciones.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import publicaciones.dto.PaperDto;
import publicaciones.dto.ResponseDto;
import publicaciones.service.PaperService;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/papers")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @PostMapping
    public ResponseDto crearPaper(@RequestBody PaperDto dto) {
        return paperService.crearPaper(dto);
    }

    @GetMapping
    public List<ResponseDto> listarPapers() {
        return paperService.listarPapers();
    }

    @GetMapping("/{id}")
    public ResponseDto obtenerPaperPorId(@PathVariable Long id) {
        return paperService.obtenerPaperPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarPaper(@PathVariable Long id, @RequestBody PaperDto dto) {
        return paperService.actualizarPaper(id, dto); // 👈 autorId también viene en el body
    }

    @DeleteMapping("/{id}")
    public ResponseDto eliminarPaper(@PathVariable Long id) {
        return paperService.eliminarPaper(id);
    }
}