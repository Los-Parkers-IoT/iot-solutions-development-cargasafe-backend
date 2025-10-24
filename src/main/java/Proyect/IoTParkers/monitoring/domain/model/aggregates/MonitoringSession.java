package Proyect.IoTParkers.monitoring.domain.model.aggregates;

import Proyect.IoTParkers.monitoring.domain.model.entities.MonitoringSessionStatus;
import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import Proyect.IoTParkers.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor

public class MonitoringSession extends AuditableAbstractAggregateRoot <MonitoringSession> {
    private String deviceId;
    private String tripId;
    private Date startTime;
    private Date endTime;
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "monitoring_session_status_id", referencedColumnName = "id",nullable = false)
    private MonitoringSessionStatus sessionStatus;

    @OneToMany(mappedBy = "telemetry", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<TelemetryData> telemetry = new ArrayList<>();

    public MonitoringSession(MonitoringSessionStatus sessionStatus, Date createdAt, Date endTime, String tripId, String deviceId, Date startTime) {
        this.sessionStatus = sessionStatus;
        this.createdAt = createdAt;
        this.endTime = endTime;
        this.tripId = tripId;
        this.deviceId = deviceId;
        this.startTime = startTime;
    }
}
