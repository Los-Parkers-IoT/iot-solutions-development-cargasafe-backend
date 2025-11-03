package Proyect.IoTParkers.fleet.interfaces.rest.transformers;


import Proyect.IoTParkers.fleet.domain.model.commands.UpdateDeviceOnlineStatusCommand;
import Proyect.IoTParkers.fleet.interfaces.rest.resources.UpdateDeviceOnlineStatusResource;

public class UpdateDeviceOnlineStatusCommandFromResourceAssembler {

    public static UpdateDeviceOnlineStatusCommand toCommandFromResource(
            Long id,
            UpdateDeviceOnlineStatusResource resource
    ) {
        return new UpdateDeviceOnlineStatusCommand(id, resource.online());
    }
}