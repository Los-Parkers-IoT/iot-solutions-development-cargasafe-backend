package Proyect.IoTParkers.fleet.domain.model.queries;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record GetDeviceByImeiQuery(String imei) {
    public GetDeviceByImeiQuery {
        imei = Preconditions.requireNonBlank(imei, "IMEI");
    }
}
