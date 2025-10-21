package Proyect.IoTParkers.alerts.domain.model.entities;

import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "alert_status")
public class AlertStatus extends AuditableModel {

    private String name;

    public AlertStatus(String name) {
        this.name = name;
    }
}
