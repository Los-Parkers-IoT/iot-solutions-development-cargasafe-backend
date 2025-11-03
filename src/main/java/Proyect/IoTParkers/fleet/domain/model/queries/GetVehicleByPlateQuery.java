package Proyect.IoTParkers.fleet.domain.model.queries;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record GetVehicleByPlateQuery(String plate) {
    public GetVehicleByPlateQuery {
        plate = Preconditions.requireNonBlank(plate, "Plate");
    }
}
