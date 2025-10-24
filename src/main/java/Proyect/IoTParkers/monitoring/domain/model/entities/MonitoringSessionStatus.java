package Proyect.IoTParkers.monitoring.domain.model.entities;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "monitoring_session_status")
public class MonitoringSessionStatus extends AuditableModel {
    private String name;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<MonitoringSession> sessions = new ArrayList<>();

    public MonitoringSessionStatus(String name) {
        this.name = name;
    }
}

