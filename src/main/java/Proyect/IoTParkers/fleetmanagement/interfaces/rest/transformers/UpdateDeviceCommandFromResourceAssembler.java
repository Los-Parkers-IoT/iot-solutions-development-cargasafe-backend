package Proyect.IoTParkers.fleetmanagement.interfaces.rest.transformers;

import Proyect.IoTParkers.fleetmanagement.domain.model.commands.UpdateDeviceCommand;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.DeviceType;
import Proyect.IoTParkers.fleetmanagement.interfaces.rest.resources.UpdateDeviceResource;

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
