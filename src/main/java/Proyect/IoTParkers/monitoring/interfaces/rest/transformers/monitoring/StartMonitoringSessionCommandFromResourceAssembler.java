package Proyect.IoTParkers.monitoring.interfaces.rest.transformers.monitoring;

import Proyect.IoTParkers.monitoring.domain.model.commands.StartMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.interfaces.rest.resources.monitoring.StartMonitoringSessionResource;

public class StartMonitoringSessionCommandFromResourceAssembler {
    public static StartMonitoringSessionCommand toCommandFromResource(StartMonitoringSessionResource resource) {
        return new StartMonitoringSessionCommand(resource.tripId(), resource.deviceId());
    }
}