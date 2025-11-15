package Proyect.IoTParkers.monitoring.interfaces.rest.transformers.telemetry;

import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import Proyect.IoTParkers.monitoring.interfaces.rest.resources.telemetry.TelemetryDataResource;

public class TelemetryDataResourceFromEntityAssembler {
    public static TelemetryDataResource toResourceFromEntity(TelemetryData entity) {
        return new TelemetryDataResource(
                entity.getId(),
                entity.getTemperature(),
                entity.getHumidity(),
                entity.getVibration(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null
        );
    }
}