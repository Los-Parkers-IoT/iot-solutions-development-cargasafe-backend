package Proyect.IoTParkers.iam.application.internal.commandservices;

import Proyect.IoTParkers.iam.application.internal.outboundservices.hashing.HashingService;
import Proyect.IoTParkers.iam.domain.model.aggregates.User;
import Proyect.IoTParkers.iam.domain.model.commands.SignInCommand;
import Proyect.IoTParkers.iam.domain.model.commands.SignUpCommand;
import Proyect.IoTParkers.iam.domain.services.UserCommandService;
import Proyect.IoTParkers.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import Proyect.IoTParkers.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import Proyect.IoTParkers.iam.infrastructure.tokens.jwt.BearerTokenService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;


import java.util.Optional;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final BearerTokenService tokenService;

    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository,
                                  HashingService hashingService,
                                  BearerTokenService tokenService,
                                  RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }


    /**
     * Handle the sign-in command
     * <p>
     *     This method handles the {@link SignInCommand} command and returns the user and the token.
     * </p>
     * @param command the sign-in command containing the username and password
     * @return and optional containing the user matching the username and the generated token
     * @throws RuntimeException if the user is not found or the password is invalid
     */

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!hashingService.matches(command.password(), user.getPassword()))
            throw new RuntimeException("Invalid password");

        String token = String.valueOf(tokenService.allocateToken(user.getUsername()));
        return Optional.of(ImmutablePair.of(user, token));
    }


    /**
     * Handle the sign-up command
     * <p>
     *     This method handles the {@link SignUpCommand} command and returns the user.
     * </p>
     * @param command the sign-up command containing the username and password
     * @return the created user
     */

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");

        var roles = command.roles().stream()
                .map(role -> roleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role name not found")))
                .toList();

        var user = new User(command.username(), hashingService.encode(command.password()), roles);
        userRepository.save(user);
        return userRepository.findByUsername(command.username());
    }


}
