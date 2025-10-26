package Proyect.IoTParkers.alerts.interfaces.rest.resources;

import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertStatus;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertType;

public record AlertResource(
        Long id,
        AlertType alertType,
        AlertStatus alertStatus
) {
}
