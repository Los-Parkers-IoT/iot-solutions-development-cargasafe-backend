package Proyect.IoTParkers.iam.infrastructure.authorization.sfs.pipeline;

import Proyect.IoTParkers.iam.infrastructure.authorization.sfs.model.UsernamePasswordAuthenticationTokenBuilder;
import Proyect.IoTParkers.iam.infrastructure.tokens.InMemoryTokenBlocklist;
import Proyect.IoTParkers.iam.infrastructure.tokens.jwt.BearerTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Bearer Authorization Request Filter.
 * <p>
 * This class is responsible for filtering requests and setting the user authentication.
 * It extends the OncePerRequestFilter class.
 * </p>
 * @see OncePerRequestFilter
 */
public class BearerAuthorizationRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BearerAuthorizationRequestFilter.class);

    private final BearerTokenService tokenService;
    private final InMemoryTokenBlocklist blocklist;

    @Qualifier("defaultUserDetailsService")
    private final UserDetailsService userDetailsService;

    public BearerAuthorizationRequestFilter(BearerTokenService tokenService,
                                            InMemoryTokenBlocklist blocklist,
                                            UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.blocklist = blocklist;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {

            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                String token = tokenService.getBearerTokenFrom(request);
                if (token != null && tokenService.validateToken(token)) {

                    // Aceptar solo ACCESS (por si alguien manda un refresh en Authorization)
                    if (!tokenService.isRefreshToken(token)) {
                        // Chequear denylist (revocado)
                        String jti = tokenService.getJti(token);
                        if (blocklist.isAccessBlocked(jti)) {
                            LOGGER.info("Access token revocado (jti: {}).", jti);
                            // Opción A: seguir la cadena como anónimo
                            // filterChain.doFilter(request, response); return;

                            // Opción B (recomendada): responder 401
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            return;
                        }

                        // Autenticar
                        String userId = tokenService.getUsernameFromToken(token);
                        var userDetails = userDetailsService.loadUserByUsername(userId);
                        SecurityContextHolder.getContext().setAuthentication(
                                UsernamePasswordAuthenticationTokenBuilder.build(userDetails, request)
                        );
                    } else {
                        LOGGER.debug("A refresh token was received in Authorization. Ignoring for authentication.");
                    }
                } else {
                    LOGGER.debug("No token or invalid token.");
                }
            }
        } catch (Exception e) {
            LOGGER.error("Authentication could not be established: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}

