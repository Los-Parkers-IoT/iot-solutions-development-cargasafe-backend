package Proyect.IoTParkers.trip.interfaces.rest.transformers;

import Proyect.IoTParkers.trip.domain.model.commands.CreateOriginPointCommand;
import Proyect.IoTParkers.trip.interfaces.rest.resources.CreateOriginPointResource;

public final class CreateOriginPointCommandFromResourceAssembler {
    static public CreateOriginPointCommand toCommandFromResource(CreateOriginPointResource resource) {
        return new CreateOriginPointCommand(resource.name(), resource.address(), resource.latitude(), resource.longitude());
    }
}
