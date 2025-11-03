package Proyect.IoTParkers.fleet.interfaces.rest.transformers;

import Proyect.IoTParkers.fleet.domain.model.commands.UpdateDeviceCommand;
import Proyect.IoTParkers.fleet.interfaces.rest.resources.UpdateDeviceResource;

public final class UpdateDeviceCommandFromResourceAssembler {
    public static UpdateDeviceCommand toCommandFromResource(Long id, UpdateDeviceResource resource) {
        return new UpdateDeviceCommand(
                id,
                resource.firmware(),
                resource.online()
        );
    }
}
