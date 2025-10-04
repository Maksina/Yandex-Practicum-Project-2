package ru.practicum.yandex.proxy.filter;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Random;

@Slf4j
@Component
public class MigrationRoutingFilter implements GlobalFilter, Ordered {

    @Value("${gradual.migration}")
    private boolean gradualMigrationEnabled;

    @Value("${movies.migration.percent}")
    private int migrationPercent;

    @Value("${movies.service.url}")
    private String moviesServiceUrl;

    @Value("${monolith.url}")
    private String monolithUrl;

    private final Random random = new Random();

    @PostConstruct
    public void logConfig() {
        log.info("✅ MigrationRoutingFilter configuration loaded:");
        log.info("   gradual.migration = {}", gradualMigrationEnabled);
        log.info("   movies.migration.percent = {}", migrationPercent);
        log.info("   movies.service.url = {}", moviesServiceUrl);
        log.info("   monolith.url = {}", monolithUrl);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // Применяем логику только к /api/movies
        if (!path.startsWith("/api/movies")) {
            return chain.filter(exchange);
        }

        String targetBaseUrl = monolithUrl;
        if (gradualMigrationEnabled) {
            int chance = random.nextInt(100); // 0–99
            if (chance < migrationPercent) {
                targetBaseUrl = moviesServiceUrl;
            }
        }

        // Пересобираем URI с новым базовым URL
        URI originalUri = request.getURI();
        log.info("🔍 originalUri: {}", originalUri);
        String newPath = originalUri.getPath();
        log.info("🔍 newPath: {}", newPath);
        String query = originalUri.getRawQuery();
        log.info("🔍 query: {}", query);
        URI newUri = URI.create(targetBaseUrl + newPath + (query != null ? "?" + query : ""));
        log.info("🔍 newUri: {}", newUri);


        exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, newUri);
        URI finalUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        log.info("✅ Final GATEWAY_REQUEST_URL_ATTR = {}", finalUri);

        ServerHttpRequest newRequest = request.mutate().uri(newUri).build();
        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    @Override
    public int getOrder() {
        return RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER + 1;
    }
}