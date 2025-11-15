package Proyect.IoTParkers.monitoring.unit;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.commands.StartMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests for TelemetryData Entity
 * Tests telemetry data creation and attributes
 */
@DisplayName("TelemetryData - Unit Tests")
class TelemetryDataTest {

    private MonitoringSession monitoringSession;
    private LocalDateTime timestamp;

    @BeforeEach
    void setUp() {
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(456L, 123L);
        monitoringSession = new MonitoringSession(command);
        timestamp = LocalDateTime.now();
    }

    @Test
    @DisplayName("Should create telemetry data with all parameters")
    void testCreateTelemetryData() {
        // Arrange & Act
        TelemetryData telemetryData = new TelemetryData(
                25.5f, 60.0f, 0.2f, -12.0464f, -77.0428f,
                timestamp, monitoringSession
        );

        // Assert
        assertThat(telemetryData).isNotNull();
        assertThat(telemetryData.getTemperature()).isEqualTo(25.5f);
        assertThat(telemetryData.getHumidity()).isEqualTo(60.0f);
        assertThat(telemetryData.getVibration()).isEqualTo(0.2f);
        assertThat(telemetryData.getLatitude()).isEqualTo(-12.0464f);
        assertThat(telemetryData.getLongitude()).isEqualTo(-77.0428f);
        assertThat(telemetryData.getCreatedAt()).isEqualTo(timestamp);
        assertThat(telemetryData.getSession()).isEqualTo(monitoringSession);
    }

    @Test
    @DisplayName("Should create telemetry data with extreme temperature values")
    void testTelemetryDataWithExtremeTemperature() {
        // Arrange & Act
        TelemetryData coldData = new TelemetryData(
                -40.0f, 50.0f, 0.1f, -12.0464f, -77.0428f,
                timestamp, monitoringSession
        );
        TelemetryData hotData = new TelemetryData(
                60.0f, 50.0f, 0.1f, -12.0464f, -77.0428f,
                timestamp, monitoringSession
        );

        // Assert
        assertThat(coldData.getTemperature()).isEqualTo(-40.0f);
        assertThat(hotData.getTemperature()).isEqualTo(60.0f);
    }

    @Test
    @DisplayName("Should create telemetry data with extreme humidity values")
    void testTelemetryDataWithExtremeHumidity() {
        // Arrange & Act
        TelemetryData lowHumidity = new TelemetryData(
                25.0f, 0.0f, 0.1f, -12.0464f, -77.0428f,
                timestamp, monitoringSession
        );
        TelemetryData highHumidity = new TelemetryData(
                25.0f, 100.0f, 0.1f, -12.0464f, -77.0428f,
                timestamp, monitoringSession
        );

        // Assert
        assertThat(lowHumidity.getHumidity()).isEqualTo(0.0f);
        assertThat(highHumidity.getHumidity()).isEqualTo(100.0f);
    }

    @Test
    @DisplayName("Should create telemetry data with various vibration levels")
    void testTelemetryDataWithDifferentVibrationLevels() {
        // Arrange & Act
        TelemetryData noVibration = new TelemetryData(
                25.0f, 60.0f, 0.0f, -12.0464f, -77.0428f,
                timestamp, monitoringSession
        );
        TelemetryData highVibration = new TelemetryData(
                25.0f, 60.0f, 5.0f, -12.0464f, -77.0428f,
                timestamp, monitoringSession
        );

        // Assert
        assertThat(noVibration.getVibration()).isEqualTo(0.0f);
        assertThat(highVibration.getVibration()).isEqualTo(5.0f);
    }

    @Test
    @DisplayName("Should create telemetry data with valid GPS coordinates (Lima, Peru)")
    void testTelemetryDataWithValidCoordinates() {
        // Arrange & Act - Lima Centro coordinates
        TelemetryData telemetryData = new TelemetryData(
                25.5f, 60.0f, 0.2f, -12.0464f, -77.0428f,
                timestamp, monitoringSession
        );

        // Assert
        assertThat(telemetryData.getLatitude()).isBetween(-90.0f, 90.0f);
        assertThat(telemetryData.getLongitude()).isBetween(-180.0f, 180.0f);
    }

    @Test
    @DisplayName("Should maintain reference to monitoring session")
    void testTelemetryDataSessionReference() {
        // Arrange & Act
        TelemetryData telemetryData = new TelemetryData(
                25.5f, 60.0f, 0.2f, -12.0464f, -77.0428f,
                timestamp, monitoringSession
        );

        // Assert
        assertThat(telemetryData.getSession()).isNotNull();
        assertThat(telemetryData.getSession().getDeviceId()).isEqualTo("123");
        assertThat(telemetryData.getSession().getTripId()).isEqualTo("456");
    }

    @Test
    @DisplayName("Should create multiple telemetry entries with different timestamps")
    void testMultipleTelemetryEntriesWithDifferentTimestamps() {
        // Arrange
        LocalDateTime time1 = LocalDateTime.now();
        LocalDateTime time2 = time1.plusMinutes(5);
        LocalDateTime time3 = time2.plusMinutes(5);

        // Act
        TelemetryData data1 = new TelemetryData(
                25.0f, 60.0f, 0.1f, -12.0464f, -77.0428f, time1, monitoringSession
        );
        TelemetryData data2 = new TelemetryData(
                26.0f, 61.0f, 0.2f, -12.0465f, -77.0429f, time2, monitoringSession
        );
        TelemetryData data3 = new TelemetryData(
                27.0f, 62.0f, 0.3f, -12.0466f, -77.0430f, time3, monitoringSession
        );

        // Assert
        assertThat(data1.getCreatedAt()).isBefore(data2.getCreatedAt());
        assertThat(data2.getCreatedAt()).isBefore(data3.getCreatedAt());
    }
}
