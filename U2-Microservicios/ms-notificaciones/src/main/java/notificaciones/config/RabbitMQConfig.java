package notificaciones.config;

//Clase que va a actuar como cola

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue notificacionesCola(){

        return QueueBuilder.durable("notificaciones.cola").build();
    }

    @Bean
    public Queue solicitudesReloj(){
        return QueueBuilder.durable("reloj-solicitud").build();
    }

    @Bean
    public FanoutExchange relojSincronizadoExchange() {
        return new FanoutExchange("reloj-sincronizado-exchange");
    }


    @Bean
    public Queue relojSincronizadoNotificacionesQueue() {
        return QueueBuilder.durable("reloj-sincronizado.notificaciones").build();
    }

    @Bean
    public Binding bindingRelojNotificaciones(
            Queue relojSincronizadoNotificacionesQueue,
            FanoutExchange relojSincronizadoExchange) {
        return BindingBuilder.bind(relojSincronizadoNotificacionesQueue)
                .to(relojSincronizadoExchange);
    }

}
