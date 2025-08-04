package notificaciones.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import notificaciones.dto.HoraClienteDto;
import notificaciones.listener.RelojListener;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RelojProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RelojListener relojListener;

    private final String nombreNodo = "ms-catalogo";

    public void enviarHora(){
        try {
            long horaActual = relojListener.getRelojLocal();

            HoraClienteDto horaClienteDto = new HoraClienteDto(
                    nombreNodo,
                    horaActual
            );

            String json = objectMapper.writeValueAsString(horaClienteDto);
            amqpTemplate.convertAndSend("reloj-solicitud", json);

            System.out.println("+ Reportando hora sincronizada: " + horaActual);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

