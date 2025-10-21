package Proyect.IoTParkers.iam.interfaces.rest.transform;

import Proyect.IoTParkers.iam.domain.model.commands.SignUpCommand;
import Proyect.IoTParkers.iam.domain.model.entities.Role;
import Proyect.IoTParkers.iam.interfaces.rest.resources.SignUpResource;

import java.util.*;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = resource.roles() != null ? resource.roles().stream().map(name -> Role.toRoleFromName(name)).toList() : new ArrayList<Role>();
        return new SignUpCommand(resource.username(), resource.password(), roles);
    }
}
