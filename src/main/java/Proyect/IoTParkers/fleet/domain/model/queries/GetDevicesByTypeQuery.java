package Proyect.IoTParkers.fleet.domain.model.queries;

import Proyect.IoTParkers.fleet.domain.model.valueobjects.DeviceType;
import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record GetDevicesByTypeQuery(DeviceType type) {
    public GetDevicesByTypeQuery {
        Preconditions.requireNonNull(type, "DeviceType");
    }
}
