package Proyect.IoTParkers.alerts.domain.model.commands;

import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertType;

public record CreateAlertCommand (AlertType alertType) {
}
