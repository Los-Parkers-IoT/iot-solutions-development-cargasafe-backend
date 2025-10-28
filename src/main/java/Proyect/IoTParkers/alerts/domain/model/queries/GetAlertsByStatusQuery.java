package Proyect.IoTParkers.alerts.domain.model.queries;

import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertStatus;

public record GetAlertsByStatusQuery(AlertStatus status) {
}
