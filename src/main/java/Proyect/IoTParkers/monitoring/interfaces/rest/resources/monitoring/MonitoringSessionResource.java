package Proyect.IoTParkers.monitoring.interfaces.rest.resources.monitoring;

public record MonitoringSessionResource(
        Long id,
        String deviceId,
        String tripId,
        String startTime,
        String endTime,
        String status
) {}
