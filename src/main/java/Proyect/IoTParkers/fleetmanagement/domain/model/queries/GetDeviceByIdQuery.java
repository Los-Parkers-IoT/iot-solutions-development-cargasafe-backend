package Proyect.IoTParkers.fleetmanagement.domain.model.queries;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record GetDeviceByIdQuery(Long id) {
    public GetDeviceByIdQuery {
        Preconditions.requireNonNull(id, "ID");
    }
}
