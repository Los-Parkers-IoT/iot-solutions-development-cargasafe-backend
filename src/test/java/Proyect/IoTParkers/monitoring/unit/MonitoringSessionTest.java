package Proyect.IoTParkers.monitoring.unit;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.commands.StartMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import Proyect.IoTParkers.monitoring.domain.model.valueobjects.MonitoringSessionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit Tests for MonitoringSession Aggregate Root
 * Tests business logic and domain behavior
 */
@DisplayName("MonitoringSession - Unit Tests")
class MonitoringSessionTest {

    private MonitoringSession monitoringSession;
    private StartMonitoringSessionCommand command;

    @BeforeEach
    void setUp() {
        command = new StartMonitoringSessionCommand(456L, 123L);
        monitoringSession = new MonitoringSession(command);
    }

    @Test
    @DisplayName("Should create a new monitoring session with ACTIVE status")
    void testCreateMonitoringSession() {
        // Assert
        assertThat(monitoringSession).isNotNull();
        assertThat(monitoringSession.getDeviceId()).isEqualTo("123");
        assertThat(monitoringSession.getTripId()).isEqualTo("456");
        assertThat(monitoringSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.ACTIVE);
        assertThat(monitoringSession.getStartTime()).isNotNull();
        assertThat(monitoringSession.getEndTime()).isNull();
    }

    @Test
    @DisplayName("Should pause an active monitoring session")
    void testPauseActiveSession() {
        // Act
        monitoringSession.pause();

        // Assert
        assertThat(monitoringSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.PAUSED);
    }

    @Test
    @DisplayName("Should throw exception when trying to pause a non-active session")
    void testPauseNonActiveSession() {
        // Arrange
        monitoringSession.pause();

        // Act & Assert
        assertThatThrownBy(() -> monitoringSession.pause())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Can only pause an active session");
    }

    @Test
    @DisplayName("Should resume a paused monitoring session")
    void testResumePausedSession() {
        // Arrange
        monitoringSession.pause();

        // Act
        monitoringSession.resume();

        // Assert
        assertThat(monitoringSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should throw exception when trying to resume a non-paused session")
    void testResumeNonPausedSession() {
        // Act & Assert
        assertThatThrownBy(() -> monitoringSession.resume())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Can only resume a paused session");
    }

    @Test
    @DisplayName("Should complete a monitoring session")
    void testCompleteSession() {
        // Act
        monitoringSession.complete();

        // Assert
        assertThat(monitoringSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.COMPLETED);
        assertThat(monitoringSession.getEndTime()).isNotNull();
    }

    @Test
    @DisplayName("Should throw exception when trying to complete an already completed session")
    void testCompleteAlreadyCompletedSession() {
        // Arrange
        monitoringSession.complete();

        // Act & Assert
        assertThatThrownBy(() -> monitoringSession.complete())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Session is already completed");
    }

    @Test
    @DisplayName("Should return true when session is active")
    void testIsActiveWhenSessionIsActive() {
        // Assert
        assertThat(monitoringSession.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should return false when session is not active")
    void testIsActiveWhenSessionIsNotActive() {
        // Arrange
        monitoringSession.pause();

        // Assert
        assertThat(monitoringSession.isActive()).isFalse();
    }

    @Test
    @DisplayName("Should add telemetry data to an active session")
    void testAddTelemetryDataToActiveSession() {
        // Arrange
        TelemetryData telemetryData = new TelemetryData(
                25.5f, 60.0f, 0.2f, -12.0464f, -77.0428f,
                LocalDateTime.now(), monitoringSession
        );

        // Act
        monitoringSession.addTelemetryData(telemetryData);

        // Assert
        assertThat(monitoringSession.getTelemetry()).hasSize(1);
        assertThat(monitoringSession.getTelemetry()).contains(telemetryData);
    }

    @Test
    @DisplayName("Should throw exception when adding telemetry data to an inactive session")
    void testAddTelemetryDataToInactiveSession() {
        // Arrange
        monitoringSession.pause();
        TelemetryData telemetryData = new TelemetryData(
                25.5f, 60.0f, 0.2f, -12.0464f, -77.0428f,
                LocalDateTime.now(), monitoringSession
        );

        // Act & Assert
        assertThatThrownBy(() -> monitoringSession.addTelemetryData(telemetryData))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot add telemetry data to inactive session");
    }

    @Test
    @DisplayName("Should handle multiple telemetry data entries")
    void testAddMultipleTelemetryData() {
        // Arrange
        TelemetryData data1 = new TelemetryData(
                25.5f, 60.0f, 0.2f, -12.0464f, -77.0428f,
                LocalDateTime.now(), monitoringSession
        );
        TelemetryData data2 = new TelemetryData(
                26.0f, 62.0f, 0.3f, -12.0465f, -77.0429f,
                LocalDateTime.now(), monitoringSession
        );

        // Act
        monitoringSession.addTelemetryData(data1);
        monitoringSession.addTelemetryData(data2);

        // Assert
        assertThat(monitoringSession.getTelemetry()).hasSize(2);
        assertThat(monitoringSession.getTelemetry()).containsExactly(data1, data2);
    }
}
