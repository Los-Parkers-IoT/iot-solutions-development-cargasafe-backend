package Proyect.IoTParkers.monitoring.interfaces.rest.resources.telemetry;

public record TelemetryDataResource(
        Long id,
        Float temperature,
        Float humidity,
        Float vibration,
        Float latitude,
        Float longitude,
        String createdAt
) {}
