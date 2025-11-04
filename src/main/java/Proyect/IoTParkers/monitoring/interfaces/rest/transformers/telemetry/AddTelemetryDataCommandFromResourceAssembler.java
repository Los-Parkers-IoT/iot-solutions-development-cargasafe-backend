package Proyect.IoTParkers.monitoring.interfaces.rest.transformers.telemetry;

import Proyect.IoTParkers.monitoring.domain.model.commands.AddTelemetryDataCommand;
import Proyect.IoTParkers.monitoring.interfaces.rest.resources.telemetry.AddTelemetryDataResource;

public class AddTelemetryDataCommandFromResourceAssembler {
    public static AddTelemetryDataCommand toCommandFromResource(AddTelemetryDataResource resource) {
        return new AddTelemetryDataCommand(
                resource.monitoringSessionId(),
                resource.temperature(),
                resource.humidity(),
                resource.vibration(),
                resource.latitude(),
                resource.longitude()
        );
    }
}
