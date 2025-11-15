package Proyect.IoTParkers.alerts.domain.model.commands;

import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertType;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.NotificationChannel;

public record CreateAlertCommand (
        Long deliveryOrderId,
        AlertType alertType,
        String description,
        NotificationChannel notificationChannel,
        String message) {
}
