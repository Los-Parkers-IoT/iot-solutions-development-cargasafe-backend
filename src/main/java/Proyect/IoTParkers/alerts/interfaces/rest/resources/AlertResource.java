package Proyect.IoTParkers.alerts.interfaces.rest.resources;

import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertStatus;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertType;

import java.util.List;

public record AlertResource(
        Long id,
        Long deliveryOrderId,
        AlertType alertType,
        AlertStatus alertStatus,
        List<IncidentResource> incidents,
        List<NotificationResource> notification
) {
}
