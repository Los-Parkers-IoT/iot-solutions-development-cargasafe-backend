package Proyect.IoTParkers.iam.interfaces.rest.transform;

import Proyect.IoTParkers.iam.domain.model.commands.SignInCommand;
import Proyect.IoTParkers.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}
