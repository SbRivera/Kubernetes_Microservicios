package publicaciones.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.LibroDto;
import publicaciones.dto.PaperDto;

@Service
public class CatalogoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void enviarLibroCatalogo(LibroDto dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            rabbitTemplate.convertAndSend("catalogo.cola", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarArticuloCatalogo(PaperDto dto) {
        try {
            String json = objectMapper.writeValueAsString(dto);
            rabbitTemplate.convertAndSend("catalogo.cola", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
