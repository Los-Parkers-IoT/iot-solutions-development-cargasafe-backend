package Proyect.IoTParkers.monitoring.application.internal.commandservices;

import Proyect.IoTParkers.monitoring.application.internal.outboundservices.acl.ExternalAlertService;
import Proyect.IoTParkers.monitoring.application.internal.outboundservices.acl.ExternalTripService;
import Proyect.IoTParkers.monitoring.domain.model.commands.AddTelemetryDataCommand;
import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import Proyect.IoTParkers.monitoring.domain.services.ITelemetryDataCommandService;
import Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa.IMonitoringSessionRepository;
import Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa.ITelemetryDataRepository;
import Proyect.IoTParkers.trip.interfaces.acl.resources.AclTripThresholdValidationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TelemetryCommandServiceImpl implements ITelemetryDataCommandService {

    @Autowired
    private ITelemetryDataRepository telemetryDataRepository;
    @Autowired
    private IMonitoringSessionRepository monitoringSessionRepository;
    @Autowired
    private ExternalTripService externalTripService;
    @Autowired
    private ExternalAlertService externalAlertService;


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

        var validations = externalTripService.validateTripThresholds(Long.parseLong(session.getTripId()), command.temperature().doubleValue(), command.humidity().doubleValue());
        processValidations(validations);

        session.addTelemetryData(telemetry);

        try {
            telemetryDataRepository.save(telemetry);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving telemetry data: " + e.getMessage());
        }
        return telemetry;
    }

    private void processValidations(List<AclTripThresholdValidationResource> resourceList) {
        resourceList.forEach(r -> {
            r.thresholdType().forEach(t -> {
                System.out.println("Sending alert for threshold type: " + t);
                externalAlertService.sendAlertNotification(t, "Threshold Exceeded", "EMAIL", "The " + t + " threshold has been exceeded.");
            });
        });
    }
}
