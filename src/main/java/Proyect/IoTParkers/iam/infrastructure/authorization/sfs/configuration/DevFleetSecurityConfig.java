package Proyect.IoTParkers.iam.infrastructure.authorization.sfs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


//Clase para probar los endpoints sin jwt token en dev
@Configuration
@Profile("dev")
public class DevFleetSecurityConfig {

    @Bean
    @org.springframework.core.annotation.Order(0) // ✅ prioridad más alta
    SecurityFilterChain fleetOnlyPermitAll(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/v1/fleet/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}