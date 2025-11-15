package Proyect.IoTParkers.fleet.interfaces.rest.transformers;

import Proyect.IoTParkers.fleet.domain.model.commands.CreateDeviceCommand;
import Proyect.IoTParkers.fleet.interfaces.rest.resources.CreateDeviceResource;

public final class CreateDeviceCommandFromResourceAssembler {
    public static CreateDeviceCommand toCommandFromResource(CreateDeviceResource resource) {
        return new CreateDeviceCommand(
                resource.imei(),
                resource.firmware(),
                resource.online());
    }
}
