package notificaciones.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import notificaciones.dto.HoraSincronizadaDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RelojListener {

    private volatile long horaSincronizada;
    private volatile long momentoRecepcion;

    @RabbitListener(queues = "reloj-sincronizado.notificaciones")
    public void recibirHoraSincronizada(String mensajeJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            HoraSincronizadaDto dto = mapper.readValue(mensajeJson, HoraSincronizadaDto.class);

            horaSincronizada = dto.getHoraSincronizada();
            momentoRecepcion = System.currentTimeMillis();

            System.out.println("- Reloj sincronizado actualizado: " + new java.util.Date(horaSincronizada));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getRelojLocal() {
        if (horaSincronizada == 0) {
            return System.currentTimeMillis();
        }
        long ahora = System.currentTimeMillis();
        return horaSincronizada + (ahora - momentoRecepcion);
    }
}

