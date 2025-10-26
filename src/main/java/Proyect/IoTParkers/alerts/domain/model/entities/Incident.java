package Proyect.IoTParkers.alerts.domain.model.entities;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.commands.CreateIncidentFromAlertCommand;
import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class Incident extends AuditableModel {

    @ManyToOne
    @NotNull
    @JoinColumn(name = "alert_id", referencedColumnName = "id",nullable = false)
    private Alert alert;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime acknowledgedAt;
    private LocalDateTime closedAt;


    public Incident(Alert alert, String description, LocalDateTime createdAt, LocalDateTime acknowledgedAt, LocalDateTime closedAt) {
        this.alert = alert;
        this.description = description;
        this.createdAt = createdAt;
        this.acknowledgedAt = acknowledgedAt;
        this.closedAt = closedAt;
    }

    public Incident(CreateIncidentFromAlertCommand command, Alert alert){
        this.alert = alert;
        this.description = command.description();
        this.createdAt = LocalDateTime.now();
        this.acknowledgedAt = null;
        this.closedAt = null;
    }

}
