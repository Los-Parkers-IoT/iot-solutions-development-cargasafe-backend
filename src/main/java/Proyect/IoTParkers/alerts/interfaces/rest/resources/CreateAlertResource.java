package Proyect.IoTParkers.alerts.interfaces.rest.resources;

import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertType;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.NotificationChannel;

public record CreateAlertResource(
        AlertType alertType,
        String description,
        NotificationChannel notificationChannel,
        String message) {
}
