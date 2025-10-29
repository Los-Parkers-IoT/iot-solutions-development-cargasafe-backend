package Proyect.IoTParkers.monitoring.domain.model.entities;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "monitoring_session_status")
public class MonitoringSessionStatus extends AuditableModel {

    @Column(nullable = false, unique = true)
    private String name;

    public MonitoringSessionStatus(String name) {
        this.name = name;
    }
}