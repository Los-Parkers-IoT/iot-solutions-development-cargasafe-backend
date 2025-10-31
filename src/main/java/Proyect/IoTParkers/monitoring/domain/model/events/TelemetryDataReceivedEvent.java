package Proyect.IoTParkers.monitoring.domain.model.events;

import java.time.LocalDateTime;

public record TelemetryDataReceivedEvent(
        Long sessionId,
        Float temperature,
        Float humidity,
        Float vibration,
        Float latitude,
        Float longitude,
        LocalDateTime receivedAt
) {}