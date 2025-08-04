package notificaciones.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import notificaciones.entity.Notificacion;
import notificaciones.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @GetMapping
    public List<Notificacion> listarTodas(){
        return service.listarTodas();
    }
}
