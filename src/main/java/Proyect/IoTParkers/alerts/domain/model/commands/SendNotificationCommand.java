package Proyect.IoTParkers.alerts.domain.model.commands;

import Proyect.IoTParkers.alerts.domain.model.valueobjects.NotificationChannel;

public record SendNotificationCommand (Long alertId, NotificationChannel notificationChannel, String message) {
}
