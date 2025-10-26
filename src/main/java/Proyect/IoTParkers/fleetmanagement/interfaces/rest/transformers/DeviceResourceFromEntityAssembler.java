package Proyect.IoTParkers.fleetmanagement.interfaces.rest.transformers;

import Proyect.IoTParkers.fleetmanagement.domain.model.aggregates.Device;
import Proyect.IoTParkers.fleetmanagement.interfaces.rest.resources.DeviceResource;

public final class DeviceResourceFromEntityAssembler {
    public static DeviceResource toResourceFromEntity(Device entity) {
        return new DeviceResource(
                entity.getId(),
                entity.getImei().value(),
                entity.getType().name(),
                entity.getFirmware().value(),
                entity.isOnline(),
                entity.getVehiclePlate() != null ? entity.getVehiclePlate().value() : null
        );
    }
}
