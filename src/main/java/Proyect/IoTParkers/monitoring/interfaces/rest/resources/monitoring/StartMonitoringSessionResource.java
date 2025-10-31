package Proyect.IoTParkers.monitoring.interfaces.rest.resources.monitoring;

public record StartMonitoringSessionResource(
        Long tripId,
        Long deviceId
) {}
