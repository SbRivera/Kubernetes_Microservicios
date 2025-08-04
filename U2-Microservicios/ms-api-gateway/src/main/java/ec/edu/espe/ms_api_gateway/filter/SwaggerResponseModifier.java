package ec.edu.espe.ms_api_gateway.filter;

import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerResponseModifier {

    @Bean
    public RouteLocator swaggerRouteLocator(RouteLocatorBuilder builder,
                                            ModifyResponseBodyGatewayFilterFactory filterFactory) {

        return builder.routes()
                .route("swagger-auth-docs", r -> r.path("/swagger/auth/v3/api-docs")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(filterFactory.apply(
                                new ModifyResponseBodyGatewayFilterFactory.Config()
                                        .setRewriteFunction(String.class, String.class, (exchange, original) -> {
                                            String replaced = original.replaceAll(
                                                    "\"servers\"\\s*:\\s*\\[[^\\]]*\\]",
                                                    "\"servers\":[{\"url\":\"/api\"}]"
                                            );
                                            if (!replaced.contains("\"servers\"")) {
                                                replaced = replaced.replaceFirst("\\{", "{\"servers\":[{\"url\":\"/api\"}],");
                                            }
                                            return reactor.core.publisher.Mono.just(replaced);
                                        })
                        )))
                        .uri("lb://AUTH-SERVICE"))

                .route("swagger-publicaciones-docs", r -> r.path("/swagger/publicaciones/v3/api-docs")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(filterFactory.apply(
                                new ModifyResponseBodyGatewayFilterFactory.Config()
                                        .setRewriteFunction(String.class, String.class, (exchange, original) -> {
                                            String replaced = original.replaceAll(
                                                    "\"servers\"\\s*:\\s*\\[[^\\]]*\\]",
                                                    "\"servers\":[{\"url\":\"/api\"}]"
                                            );
                                            if (!replaced.contains("\"servers\"")) {
                                                replaced = replaced.replaceFirst("\\{", "{\"servers\":[{\"url\":\"/api\"}],");
                                            }
                                            return reactor.core.publisher.Mono.just(replaced);
                                        })
                        )))
                        .uri("lb://SERVICIO-PUBLICACIONES"))

                .route("swagger-notificaciones-docs", r -> r.path("/swagger/notificaciones/v3/api-docs")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(filterFactory.apply(
                                new ModifyResponseBodyGatewayFilterFactory.Config()
                                        .setRewriteFunction(String.class, String.class, (exchange, original) -> {
                                            String replaced = original.replaceAll(
                                                    "\"servers\"\\s*:\\s*\\[[^\\]]*\\]",
                                                    "\"servers\":[{\"url\":\"/api\"}]"
                                            );
                                            if (!replaced.contains("\"servers\"")) {
                                                replaced = replaced.replaceFirst("\\{", "{\"servers\":[{\"url\":\"/api\"}],");
                                            }
                                            return reactor.core.publisher.Mono.just(replaced);
                                        })
                        )))
                        .uri("lb://SERVICIO-NOTIFICACIONES"))

                .route("swagger-catalogo-docs", r -> r.path("/swagger/catalogo/v3/api-docs")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(filterFactory.apply(
                                new ModifyResponseBodyGatewayFilterFactory.Config()
                                        .setRewriteFunction(String.class, String.class, (exchange, original) -> {
                                            String replaced = original.replaceAll(
                                                    "\"servers\"\\s*:\\s*\\[[^\\]]*\\]",
                                                    "\"servers\":[{\"url\":\"/api\"}]"
                                            );
                                            if (!replaced.contains("\"servers\"")) {
                                                replaced = replaced.replaceFirst("\\{", "{\"servers\":[{\"url\":\"/api\"}],");
                                            }
                                            return reactor.core.publisher.Mono.just(replaced);
                                        })
                        )))
                        .uri("lb://SERVICIO-CATALOGO"))

                .build();
    }
}
