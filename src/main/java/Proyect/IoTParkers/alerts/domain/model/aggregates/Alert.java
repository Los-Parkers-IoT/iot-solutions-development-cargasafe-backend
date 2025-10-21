package Proyect.IoTParkers.alerts.domain.model.aggregates;

import Proyect.IoTParkers.alerts.domain.model.entities.AlertStatus;
import Proyect.IoTParkers.alerts.domain.model.entities.AlertType;
import Proyect.IoTParkers.alerts.domain.model.entities.Incident;
import Proyect.IoTParkers.alerts.domain.model.entities.Notification;
import Proyect.IoTParkers.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Alert extends AuditableAbstractAggregateRoot<Alert> {

    @ManyToOne
    @JoinColumn(name = "alert_type_id",nullable = false)
    private AlertType alertType;

    @ManyToOne
    @JoinColumn(name = "alert_status_id",nullable = false)
    private AlertStatus alertStatus;

    @OneToMany(mappedBy = "alert", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Incident> incidents = new ArrayList<>();

    @OneToMany(mappedBy = "alert", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Notification> notifications = new ArrayList<>();

    public Alert(AlertType alertType, AlertStatus alertStatus) {
        this.alertType = alertType;
        this.alertStatus = alertStatus;
    }
}
