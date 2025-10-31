package Proyect.IoTParkers.monitoring.domain.model.commands;

public record AddTelemetryDataCommand(
        Long monitoringSessionId,
        Float temperature,
        Float humidity,
        Float vibration,
        Float latitude,
        Float longitude
) {}
