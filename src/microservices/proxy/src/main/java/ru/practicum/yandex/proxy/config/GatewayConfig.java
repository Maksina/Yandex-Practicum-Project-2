package ru.practicum.yandex.proxy.config;

import ru.practicum.yandex.proxy.filter.MigrationRoutingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${monolith.url}")
    private String monolithUrl;

    @Value("${events.service.url}")
    private String eventsServiceUrl;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        // НЕ передаём фильтр сюда — он глобальный
        return builder.routes()
                .route("events_route", r -> r
                        .path("/api/events/**")
                        .uri(eventsServiceUrl)
                )
                .route("movies_route", r -> r
                        .path("/api/movies/**")
                        .uri(monolithUrl) // fallback URI (не используется в фильтре)
                )
                .route("fallback_route", r -> r
                        .path("/**")
                        .uri(monolithUrl)
                )
                .build();
    }
}