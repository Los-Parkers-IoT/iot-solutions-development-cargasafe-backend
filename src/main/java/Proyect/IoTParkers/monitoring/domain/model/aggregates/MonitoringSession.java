package Proyect.IoTParkers.monitoring.domain.model.aggregates;

import Proyect.IoTParkers.monitoring.domain.model.commands.StartMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.entities.MonitoringSessionStatus;
import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import Proyect.IoTParkers.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class MonitoringSession extends AuditableAbstractAggregateRoot<MonitoringSession> {

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "trip_id", nullable = false)
    private String tripId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "monitoring_session_status_id", referencedColumnName = "id", nullable = false)
    private MonitoringSessionStatus sessionStatus;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TelemetryData> telemetry = new ArrayList<>();

    public MonitoringSession(StartMonitoringSessionCommand command, MonitoringSessionStatus activeStatus) {
        this.deviceId = command.deviceId().toString();
        this.tripId = command.tripId().toString();
        this.startTime = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.sessionStatus = activeStatus;
    }

    public void pause(MonitoringSessionStatus pausedStatus) {
        if (!"ACTIVE".equals(this.sessionStatus.getName())) {
            throw new IllegalStateException("Only active sessions can be paused");
        }
        this.sessionStatus = pausedStatus;
    }

    public void resume(MonitoringSessionStatus activeStatus) {
        if (!"PAUSED".equals(this.sessionStatus.getName())) {
            throw new IllegalStateException("Only paused sessions can be resumed");
        }
        this.sessionStatus = activeStatus;
    }

    public void complete(MonitoringSessionStatus completedStatus) {
        if ("COMPLETED".equals(this.sessionStatus.getName())) {
            throw new IllegalStateException("Session is already completed");
        }
        this.sessionStatus = completedStatus;
        this.endTime = LocalDateTime.now();
    }

    public boolean isActive() {
        return "ACTIVE".equals(this.sessionStatus.getName());
    }

    public void addTelemetryData(TelemetryData telemetryData) {
        if (!isActive()) {
            throw new IllegalStateException("Cannot add telemetry data to inactive session");
        }
        this.telemetry.add(telemetryData);
    }
}