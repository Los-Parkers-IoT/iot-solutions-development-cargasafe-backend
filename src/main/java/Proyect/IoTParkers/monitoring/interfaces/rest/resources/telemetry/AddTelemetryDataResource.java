package Proyect.IoTParkers.monitoring.interfaces.rest.resources.telemetry;

public record AddTelemetryDataResource(
        Long monitoringSessionId,
        Float temperature,
        Float humidity,
        Float vibration,
        Float latitude,
        Float longitude
) {}
