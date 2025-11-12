package Proyect.IoTParkers.iam.infrastructure.authorization.sfs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración temporal para desactivar toda la seguridad.
 */
@Configuration
@Order(0) // prioridad más alta
public class DisableSecurityConfiguration {

    @Bean
    public SecurityFilterChain disableAllSecurity(HttpSecurity http) throws Exception {
        System.out.println("🟢 DisableSecurityConfiguration applied — All endpoints are public.");

        http
                .securityMatcher("/**") // intercepta TODAS las rutas
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .headers(headers -> headers.disable())
                .securityContext(securityContext -> securityContext.disable())
                .sessionManagement(session -> session.disable());
        return http.build();
    }
}
