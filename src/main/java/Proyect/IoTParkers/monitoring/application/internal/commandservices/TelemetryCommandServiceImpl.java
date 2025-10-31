package Proyect.IoTParkers.monitoring.application.internal.commandservices;

import Proyect.IoTParkers.monitoring.domain.model.commands.AddTelemetryDataCommand;
import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import Proyect.IoTParkers.monitoring.domain.services.ITelemetryDataCommandService;
import Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa.IMonitoringSessionRepository;
import Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa.ITelemetryDataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TelemetryCommandServiceImpl implements ITelemetryDataCommandService {

    private final ITelemetryDataRepository telemetryDataRepository;
    private final IMonitoringSessionRepository monitoringSessionRepository;

    public TelemetryCommandServiceImpl(
            ITelemetryDataRepository telemetryDataRepository,
            IMonitoringSessionRepository monitoringSessionRepository) {
        this.telemetryDataRepository = telemetryDataRepository;
        this.monitoringSessionRepository = monitoringSessionRepository;
    }

    @Override
    public TelemetryData handle(AddTelemetryDataCommand command) {
        var session = monitoringSessionRepository.findById(command.monitoringSessionId())
                .orElseThrow(() -> new IllegalArgumentException("Monitoring session not found"));

        var telemetry = new TelemetryData(
                command.temperature(),
                command.humidity(),
                command.vibration(),
                command.latitude(),
                command.longitude(),
                LocalDateTime.now(),
                session
        );

        session.addTelemetryData(telemetry);

        try {
            telemetryDataRepository.save(telemetry);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving telemetry data: " + e.getMessage());
        }
        return telemetry;
    }
}
