package Proyect.IoTParkers.fleet.interfaces.rest.transformers;

import Proyect.IoTParkers.fleet.domain.model.commands.UpdateDeviceCommand;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.DeviceType;
import Proyect.IoTParkers.fleet.interfaces.rest.resources.UpdateDeviceResource;

public final class UpdateDeviceCommandFromResourceAssembler {
    public static UpdateDeviceCommand toCommandFromResource(Long id, UpdateDeviceResource resource) {
        return new UpdateDeviceCommand(
                id,
                resource.type() != null ? DeviceType.valueOf(resource.type()) : null,
                resource.firmware(),
                resource.online()
        );
    }
}
