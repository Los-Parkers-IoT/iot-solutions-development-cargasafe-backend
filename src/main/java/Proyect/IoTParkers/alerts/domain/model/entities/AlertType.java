package Proyect.IoTParkers.alerts.domain.model.entities;

import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "alert_type")
public class AlertType extends AuditableModel {

    @Column(nullable = false, unique = true)
    private String name;

    public AlertType(String name){
        this.name = name;
    }

}
