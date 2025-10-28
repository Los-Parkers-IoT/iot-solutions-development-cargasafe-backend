package Proyect.IoTParkers.iam.interfaces.rest.transform;

import Proyect.IoTParkers.iam.domain.model.entities.Role;
import Proyect.IoTParkers.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}
