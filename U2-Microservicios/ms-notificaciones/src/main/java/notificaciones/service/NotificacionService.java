package notificaciones.service;

import notificaciones.dto.NotificacionDto;
import notificaciones.entity.Notificacion;
import notificaciones.listener.RelojListener;
import notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service

public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    public List<Notificacion> listarTodas(){
        return repository.findAll();
    }

    @Autowired
    private RelojListener relojListener;


    public void guardar(NotificacionDto dto){
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setTipo(dto.getTipo());

        LocalDateTime fechaSincronizada =
                Instant.ofEpochMilli(relojListener.getRelojLocal())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

        notificacion.setFecha(fechaSincronizada);

        repository.save(notificacion);
    }

}
