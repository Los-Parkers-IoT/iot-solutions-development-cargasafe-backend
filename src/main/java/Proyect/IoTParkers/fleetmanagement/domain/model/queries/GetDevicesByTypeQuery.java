package Proyect.IoTParkers.fleetmanagement.domain.model.queries;

import Proyect.IoTParkers.fleetmanagement.domain.model.valueobjects.DeviceType;
import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record GetDevicesByTypeQuery(DeviceType type) {
    public GetDevicesByTypeQuery {
        Preconditions.requireNonNull(type, "DeviceType");
    }
}
