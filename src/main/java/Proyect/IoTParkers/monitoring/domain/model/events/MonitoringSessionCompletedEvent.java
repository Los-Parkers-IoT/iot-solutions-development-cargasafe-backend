package Proyect.IoTParkers.monitoring.domain.model.events;

import java.time.LocalDateTime;

public record MonitoringSessionCompletedEvent(
        Long sessionId,
        LocalDateTime completedAt
) {}
