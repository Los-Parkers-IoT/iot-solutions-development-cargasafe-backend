package Proyect.IoTParkers.iam.interfaces.rest;

import Proyect.IoTParkers.iam.domain.services.UserCommandService;
import Proyect.IoTParkers.iam.infrastructure.tokens.InMemoryTokenBlocklist;
import Proyect.IoTParkers.iam.infrastructure.tokens.jwt.BearerTokenService;
import Proyect.IoTParkers.iam.interfaces.rest.resources.*;
import Proyect.IoTParkers.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import Proyect.IoTParkers.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import Proyect.IoTParkers.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/auth/sign-in</li>
 *         <li>POST /api/v1/auth/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {
    private final UserCommandService userCommandService;
    private final BearerTokenService tokens;
    private final InMemoryTokenBlocklist blocklist;

    public AuthenticationController(UserCommandService userCommandService,
                                    BearerTokenService tokens,
                                    InMemoryTokenBlocklist blocklist) {
        this.userCommandService = userCommandService;
        this.tokens = tokens;
        this.blocklist = blocklist;
    }


    @PostMapping("/sign-in")
    public ResponseEntity<TokenPairResource> signIn(@RequestBody SignInResource signInResource) {
        var cmd = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var result = userCommandService.handle(cmd);
        if (result.isEmpty()) return ResponseEntity.notFound().build();

        var user = result.get().getLeft();   // entidad User
        var username = user.getUsername();

        // opcional: si quieres guardar el ID también, lo pones en claims
        var access  = tokens.allocateAccessToken(username, Map.of("typ","access", "uid", user.getId()));
        var refresh = tokens.allocateRefreshToken(username); // ya pone typ=refresh

        // si ya estabas devolviendo AuthenticatedUserResource, añádele access/refresh;
        // si ya hiciste el TokenPairResource, deja así:
        return ResponseEntity.ok(new TokenPairResource(access, refresh));
    }
    /**
     * Handles the sign-up request.
     * @param signUpResource the sign-up request body.
     * @return the created user resource.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<UserResource> signUp(@RequestBody @Valid SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);

    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenResource body) {
        String rt = body.refreshToken();
        if (rt == null || !tokens.validateToken(rt) || !tokens.isRefreshToken(rt)) {
            return ResponseEntity.status(401).build();
        }
        // ¿ya fue revocado este refresh?
        String jti = tokens.getJti(rt);
        if (blocklist.isRefreshRevoked(jti)) {
            return ResponseEntity.status(401).build();
        }

        String userId = tokens.getUsernameFromToken(rt);
        // Emite un par nuevo (rotación sencilla)
        String newAccess  = tokens.allocateAccessToken(userId, Map.of());
        String newRefresh = tokens.allocateRefreshToken(userId);

        // (opcional) revoca el RT viejo al rotar
        // blocklist.revokeRefresh(jti, Jwts.parser().verifyWith(((TokenServiceImpl)tokens).getSigningKey())...);

        return ResponseEntity.ok(new TokenPairResource(newAccess, newRefresh));
    }


    @PostMapping("/revoke")
    public ResponseEntity<Void> revoke(HttpServletRequest req, @RequestBody RevokeRequest body) {
        // 1) revocar ACCESS del header si viene
        String access = tokens.getBearerTokenFrom(req);
        if (access != null && tokens.validateToken(access)) {
            String accessJti = tokens.getJti(access);
            var exp = tokens.getExpiration(access);
            blocklist.blockAccess(accessJti, exp.toInstant());
        }

        // 2) revocar REFRESH del body
        if (body.refreshToken() != null && tokens.validateToken(body.refreshToken()) && tokens.isRefreshToken(body.refreshToken())) {
            String refreshJti = tokens.getJti(body.refreshToken());
            var exp = tokens.getExpiration(body.refreshToken());
            blocklist.revokeRefresh(refreshJti, exp.toInstant());
        }

        return ResponseEntity.noContent().build();
    }



}
