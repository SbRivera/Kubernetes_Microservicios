package ec.edu.espe.ms_api_gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class SwaggerServicesController {

    @GetMapping("/swagger-config")
    public Map<String, Object> swaggerConfig() {
        List<Map<String, String>> urls = new ArrayList<>();

        urls.add(Map.of("name", "auth-service", "url", "/swagger/auth/v3/api-docs"));
        urls.add(Map.of("name", "publicaciones", "url", "/swagger/publicaciones/v3/api-docs"));
        urls.add(Map.of("name", "notificaciones", "url", "/swagger/notificaciones/v3/api-docs"));
        urls.add(Map.of("name", "catalogo", "url", "/swagger/catalogo/v3/api-docs"));

        return Map.of("urls", urls);
    }
}
