package co.edu.uniquindio.msvc_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import reactor.core.publisher.Mono;

import java.util.Collection;

import java.util.stream.Collectors;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
//@EnableWebFluxSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception
    public SecurityFilterChain securityFilterChain(HttpSecurity http /*ServerHttpSecurity http*/) throws Exception {

        return http.authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/authorized", "/logout").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/items", "/api/products", "/api/users").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/items/{id}", "/api/products/{id}", "/api/users/{id}", "/api/users/username").hasAnyRole("USER", "ADMIN")
//                            .requestMatchers("/api/items/**", "/api/products/**", "/api/users/**")
//                            .hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.POST, "/api/items", "/api/products", "/api/users").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/items", "/api/products", "/api/users/{id}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/items/{id}", "/api/products/{id}", "/api/users/{id}").hasRole("ADMIN")
                            .anyRequest().authenticated();
                }).cors(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(login -> login.loginPage("/oauth2/authorization/client-app"))
//                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults())
               // .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults())).build();
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new Converter<Jwt, Mono<AbstractAuthenticationToken>>() {
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new Converter<Jwt, AbstractAuthenticationToken>() {

                    @Override
//                    public Mono<AbstractAuthenticationToken> convert(Jwt source) {
                    public AbstractAuthenticationToken convert(Jwt source) {
                        Collection<String> roles = source.getClaimAsStringList("roles");
                        Collection<GrantedAuthority> authorities = roles.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
//                        return Mono.just(new JwtAuthenticationToken(source, authorities));
                        return new JwtAuthenticationToken(source, authorities);
                    }
                })))
                .build();
    }
}
