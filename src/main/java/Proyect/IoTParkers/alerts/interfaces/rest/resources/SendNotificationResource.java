package Proyect.IoTParkers.alerts.interfaces.rest.resources;

import Proyect.IoTParkers.alerts.domain.model.valueobjects.NotificationChannel;

public record SendNotificationResource (
        Long alertId,
        NotificationChannel notificationChannel,
        String message

) {
}
