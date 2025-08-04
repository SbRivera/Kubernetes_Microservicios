package ms_catalogo.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ms_catalogo.entity.Catalogo;
import ms_catalogo.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    @GetMapping
    public List<Catalogo> obtenerCatalogo() {
        return catalogoService.listarCatalogo();
    }
}