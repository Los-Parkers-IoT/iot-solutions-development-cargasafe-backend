package Proyect.IoTParkers.iam.infrastructure.tokens.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface BearerTokenService {

    /** Extrae el Bearer token del header Authorization (o null si no está). */
    String getBearerTokenFrom(HttpServletRequest request);

    /** Emite un JWT para el principal autenticado. */
    String allocateToken(Authentication authentication);

    /** Emite un JWT para el subject (username/userId). */
    String allocateToken(String subject);

    /** Obtiene el subject (username/userId) desde el JWT. */
    String getUsernameFromToken(String token);

    /** Valida firma/formato/expiración del JWT. */
    boolean validateToken(String token);
}
