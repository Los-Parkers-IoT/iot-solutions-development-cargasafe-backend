package Proyect.IoTParkers.monitoring.domain.services;

import Proyect.IoTParkers.monitoring.domain.model.commands.AddTelemetryDataCommand;
import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;

public interface ITelemetryDataCommandService {
    TelemetryData handle(AddTelemetryDataCommand command);
}
