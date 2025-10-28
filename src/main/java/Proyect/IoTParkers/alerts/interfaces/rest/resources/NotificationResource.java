package Proyect.IoTParkers.alerts.interfaces.rest.resources;

import Proyect.IoTParkers.alerts.domain.model.entities.Notification;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.NotificationChannel;

import java.time.LocalDateTime;
import java.util.Date;

public record NotificationResource(
        Long id,
        Long alertId,
        NotificationChannel notificationChannel,
        String message,
        LocalDateTime sentAt
) {
}
