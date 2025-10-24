package Proyect.IoTParkers.monitoring.domain.model.entities;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class TelemetryData extends AuditableModel {
    private Float temperature;
    private Float humidity;
    private Float vibration;
    private Float latitude;
    private Float longitude;
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "monitoring_session_id", referencedColumnName = "id",nullable = false)
    private MonitoringSession session;

    public TelemetryData(Float temperature, Float humidity, Float vibration, Float latitude, Float longitude, Date createdAt, MonitoringSession session) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.vibration = vibration;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.session = session;
    }
}
