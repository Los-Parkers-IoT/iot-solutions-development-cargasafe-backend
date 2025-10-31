package Proyect.IoTParkers.monitoring.domain.model.aggregates;

import Proyect.IoTParkers.monitoring.domain.model.commands.StartMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.valueobjects.MonitoringSessionStatus;
import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import Proyect.IoTParkers.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MonitoringSessionStatus sessionStatus;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TelemetryData> telemetry = new ArrayList<>();

    public MonitoringSession(StartMonitoringSessionCommand command) {
        this.deviceId = command.deviceId().toString();
        this.tripId = command.tripId().toString();
        this.startTime = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.sessionStatus = MonitoringSessionStatus.ACTIVE;
    }

    public void pause() {
        if (this.sessionStatus != MonitoringSessionStatus.ACTIVE) {
            throw new IllegalStateException("Can only pause an active session");
        }
        this.sessionStatus = MonitoringSessionStatus.PAUSED;
    }

    public void resume() {
        if (this.sessionStatus != MonitoringSessionStatus.PAUSED) {
            throw new IllegalStateException("Can only resume a paused session");
        }
        this.sessionStatus = MonitoringSessionStatus.ACTIVE;
    }

    public void complete() {
        if (this.sessionStatus == MonitoringSessionStatus.COMPLETED) {
            throw new IllegalStateException("Session is already completed");
        }
        this.endTime = LocalDateTime.now();
        this.sessionStatus = MonitoringSessionStatus.COMPLETED;
    }

    public boolean isActive() {
        return MonitoringSessionStatus.ACTIVE.equals(this.sessionStatus);
    }

    public void addTelemetryData(TelemetryData telemetryData) {
        if (!isActive()) {
            throw new IllegalStateException("Cannot add telemetry data to inactive session");
        }
        this.telemetry.add(telemetryData);
    }
}