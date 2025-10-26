package Proyect.IoTParkers.fleetmanagement.interfaces.rest.transformers;

import Proyect.IoTParkers.fleetmanagement.domain.model.commands.CreateDeviceCommand;
import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.DeviceType;
import Proyect.IoTParkers.fleetmanagement.interfaces.rest.resources.CreateDeviceResource;

public final class CreateDeviceCommandFromResourceAssembler {
    public static CreateDeviceCommand toCommandFromResource(CreateDeviceResource resource) {
        return new CreateDeviceCommand(
                resource.imei(),
                resource.type() != null ? DeviceType.valueOf(resource.type()) : null,
                resource.firmware(),
                resource.online(),
                resource.vehiclePlate()
        );
    }
}
