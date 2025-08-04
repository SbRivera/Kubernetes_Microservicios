package publicaciones.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.NotificacionDto;
//Productor
@Service
public class NotificacionProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper ;

    public void enviarNotificacion(String mensaje, String tipo){
        try {
            NotificacionDto notificacionDto = new NotificacionDto(mensaje, tipo);
            String json = objectMapper.writeValueAsString(notificacionDto);
            rabbitTemplate.convertAndSend("notificaciones.cola", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
