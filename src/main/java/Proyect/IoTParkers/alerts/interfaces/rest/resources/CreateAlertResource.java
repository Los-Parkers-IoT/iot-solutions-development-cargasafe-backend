package Proyect.IoTParkers.alerts.interfaces.rest.resources;

import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertType;

public record CreateAlertResource(AlertType alertType) {
}
