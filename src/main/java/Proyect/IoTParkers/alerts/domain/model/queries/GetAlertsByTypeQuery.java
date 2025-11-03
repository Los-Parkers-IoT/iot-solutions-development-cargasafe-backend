package Proyect.IoTParkers.alerts.domain.model.queries;

import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertType;

public record GetAlertsByTypeQuery(AlertType type) {
}
