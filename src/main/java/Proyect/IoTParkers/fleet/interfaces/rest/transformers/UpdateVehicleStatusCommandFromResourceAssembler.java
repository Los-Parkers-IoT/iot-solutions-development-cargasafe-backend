package Proyect.IoTParkers.fleet.interfaces.rest.transformers;

import Proyect.IoTParkers.fleet.domain.model.commands.UpdateVehicleStatusCommand;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.VehicleStatus;
import Proyect.IoTParkers.fleet.interfaces.rest.resources.UpdateVehicleStatusResource;

public class UpdateVehicleStatusCommandFromResourceAssembler {

    private UpdateVehicleStatusCommandFromResourceAssembler() {
        // utility
    }

    public static UpdateVehicleStatusCommand toCommandFromResource(Long vehicleId,
                                                                   UpdateVehicleStatusResource resource) {
        var status = VehicleStatus.valueOf(resource.status());
        return new UpdateVehicleStatusCommand(vehicleId, status);
    }
}