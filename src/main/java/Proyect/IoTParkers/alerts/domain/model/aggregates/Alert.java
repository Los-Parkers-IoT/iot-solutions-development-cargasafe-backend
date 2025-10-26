package Proyect.IoTParkers.alerts.domain.model.aggregates;

import Proyect.IoTParkers.alerts.domain.model.commands.CreateAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.entities.Incident;
import Proyect.IoTParkers.alerts.domain.model.entities.Notification;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertStatus;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertType;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertType alertType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertStatus alertStatus;

    @OneToMany(mappedBy = "alert", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Incident> incidents = new ArrayList<>();

    @OneToMany(mappedBy = "alert", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Notification> notifications = new ArrayList<>();

    public Alert(AlertType alertType, AlertStatus alertStatus) {
        this.alertType = alertType;
        this.alertStatus = alertStatus;
    }

    public Alert(CreateAlertCommand command) {
        this.alertType = command.alertType();
        this.alertStatus = AlertStatus.OPEN;
    }

    public void acknowledge(AlertStatus newStatus) {
        this.alertStatus = newStatus;
    }

}
