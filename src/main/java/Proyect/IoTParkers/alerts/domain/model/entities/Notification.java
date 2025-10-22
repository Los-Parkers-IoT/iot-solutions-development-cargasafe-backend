package Proyect.IoTParkers.alerts.domain.model.entities;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.NotificationChannel;
import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class Notification extends AuditableModel {

    @ManyToOne
    @NotNull
    @JoinColumn(name = "alert_id", referencedColumnName = "id",nullable = false)
    private Alert alert;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "notification_channel_id", referencedColumnName = "id",nullable = false)
    private NotificationChannel notificationChannel;

    private String message;
    private Date sentAt;

    public Notification(Alert alert, NotificationChannel notificationChannel, String message, Date sentAt) {
        this.alert = alert;
        this.notificationChannel = notificationChannel;
        this.message = message;
        this.sentAt = sentAt;
    }
}
