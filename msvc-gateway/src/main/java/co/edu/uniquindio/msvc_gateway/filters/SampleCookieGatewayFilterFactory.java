package co.edu.uniquindio.msvc_gateway.filters;

import lombok.Data;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class SampleCookieGatewayFilterFactory extends AbstractGatewayFilterFactory<SampleCookieGatewayFilterFactory.ConfigurationCookie> {

    private final Logger logger = LoggerFactory.getLogger(SampleCookieGatewayFilterFactory.class);

    public SampleCookieGatewayFilterFactory() {
        super(ConfigurationCookie.class);
    }

    @Override
    public GatewayFilter apply(ConfigurationCookie config) {

        return new OrderedGatewayFilter((exchange, chain) -> {

            logger.info("Ejecutando pre gateway filter factory " + config.message);
            return (chain.filter(exchange).then(Mono.fromRunnable(() -> {

                Optional.ofNullable(config.value).ifPresent(cookie -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.name, cookie).build());
                });
                logger.info("Ejecutando pre gateway filter factory " + config.message);
            })));
        }, 100);

    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("name", "value", "message");
    }

    @Data
    public static class ConfigurationCookie {

        private String name;
        private String value;
        private String message;
    }
}
