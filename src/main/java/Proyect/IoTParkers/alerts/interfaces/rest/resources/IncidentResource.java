package Proyect.IoTParkers.alerts.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.Date;

public record IncidentResource (
        Long id,
        Long alertId,
        String description,
        LocalDateTime createdAt,
        LocalDateTime acknowledgeAt,
        LocalDateTime closedAt
){
}
