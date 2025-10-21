package Proyect.IoTParkers.iam.interfaces.rest.transform;

import Proyect.IoTParkers.iam.domain.model.aggregates.User;
import Proyect.IoTParkers.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
    }
}
