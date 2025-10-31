package Proyect.IoTParkers.monitoring.domain.model.events;

import java.time.LocalDateTime;

public record MonitoringSessionStartedEvent(
        Long sessionId,
        String tripId,
        String deviceId,
        LocalDateTime startedAt
) {}
