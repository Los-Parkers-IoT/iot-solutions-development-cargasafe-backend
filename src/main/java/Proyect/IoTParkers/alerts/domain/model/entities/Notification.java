package Proyect.IoTParkers.alerts.domain.model.entities;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.commands.SendNotificationCommand;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.NotificationChannel;
import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class Notification extends AuditableModel {

    @ManyToOne
    @NotNull
    @JoinColumn(name = "alert_id", referencedColumnName = "id",nullable = false)
    private Alert alert;

    @Enumerated(EnumType.STRING)
    private NotificationChannel notificationChannel;

    private String message;
    private LocalDateTime sentAt;

    public Notification(Alert alert, NotificationChannel notificationChannel, String message, LocalDateTime sentAt) {
        this.alert = alert;
        this.notificationChannel = notificationChannel;
        this.message = message;
        this.sentAt = sentAt;
    }

    public Notification(SendNotificationCommand command, Alert alert) {
        this.alert = alert;
        this.notificationChannel = command.notificationChannel();
        this.message = command.message();
        this.sentAt = LocalDateTime.now();
    }
}
