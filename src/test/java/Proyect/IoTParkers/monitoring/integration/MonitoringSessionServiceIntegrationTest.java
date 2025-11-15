package Proyect.IoTParkers.monitoring.integration;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.commands.StartMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.valueobjects.MonitoringSessionStatus;
import Proyect.IoTParkers.monitoring.domain.services.IMonitoringSessionCommandService;
import Proyect.IoTParkers.monitoring.domain.services.IMonitoringSessionQueryService;
import Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa.IMonitoringSessionRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

/**
 * Integration Tests for Monitoring Session Service
 * Tests the complete flow through command/query services and repositories
 */
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("MonitoringSession Service - Integration Tests")
class MonitoringSessionServiceIntegrationTest {

    @Autowired
    private IMonitoringSessionCommandService commandService;

    @Autowired
    private IMonitoringSessionQueryService queryService;

    @Autowired
    private IMonitoringSessionRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("Should start a new monitoring session through command service")
    @Transactional
    void testStartMonitoringSession() {
        // Arrange
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(456L, 123L);

        // Act
        MonitoringSession session = commandService.handle(command);

        // Assert
        assertThat(session).isNotNull();
        assertThat(session.getId()).isNotNull();
        assertThat(session.getDeviceId()).isEqualTo("123");
        assertThat(session.getTripId()).isEqualTo("456");
        assertThat(session.getSessionStatus()).isEqualTo(MonitoringSessionStatus.ACTIVE);
        assertThat(session.getStartTime()).isNotNull();
    }

    @Test
    @Order(2)
    @DisplayName("Should persist monitoring session to database")
    @Transactional
    void testPersistMonitoringSession() {
        // Arrange
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(101112L, 789L);

        // Act
        MonitoringSession savedSession = commandService.handle(command);
        MonitoringSession retrievedSession = repository.findById(savedSession.getId()).orElse(null);

        // Assert
        assertThat(retrievedSession).isNotNull();
        assertThat(retrievedSession.getDeviceId()).isEqualTo(savedSession.getDeviceId());
        assertThat(retrievedSession.getTripId()).isEqualTo(savedSession.getTripId());
    }

    @Test
    @Order(3)
    @DisplayName("Should create multiple monitoring sessions")
    @Transactional
    void testCreateMultipleMonitoringSessions() {
        // Arrange
        StartMonitoringSessionCommand command1 = new StartMonitoringSessionCommand(222L, 111L);
        StartMonitoringSessionCommand command2 = new StartMonitoringSessionCommand(444L, 333L);
        StartMonitoringSessionCommand command3 = new StartMonitoringSessionCommand(666L, 555L);

        // Act
        MonitoringSession session1 = commandService.handle(command1);
        MonitoringSession session2 = commandService.handle(command2);
        MonitoringSession session3 = commandService.handle(command3);

        // Assert
        assertThat(repository.count()).isEqualTo(3);
        assertThat(session1.getId()).isNotEqualTo(session2.getId());
        assertThat(session2.getId()).isNotEqualTo(session3.getId());
    }

    @Test
    @Order(4)
    @DisplayName("Should find monitoring session by device ID")
    @Transactional
    void testFindSessionByDeviceId() {
        // Arrange
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(888L, 999L);
        MonitoringSession savedSession = commandService.handle(command);

        // Act
        var foundSession = repository.findByDeviceId("999");

        // Assert
        assertThat(foundSession).isNotEmpty();
        assertThat(foundSession.get().getDeviceId()).isEqualTo("999");
        assertThat(foundSession.get().getTripId()).isEqualTo("888");
    }

    @Test
    @Order(5)
    @DisplayName("Should update monitoring session status")
    @Transactional
    void testUpdateMonitoringSessionStatus() {
        // Arrange
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(888L, 777L);
        MonitoringSession session = commandService.handle(command);

        // Act
        session.pause();
        repository.save(session);
        MonitoringSession updatedSession = repository.findById(session.getId()).orElse(null);

        // Assert
        assertThat(updatedSession).isNotNull();
        assertThat(updatedSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.PAUSED);
    }

    @Test
    @Order(6)
    @DisplayName("Should complete a monitoring session")
    @Transactional
    void testCompleteMonitoringSession() throws InterruptedException {
        // Arrange
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(666L, 555L);
        MonitoringSession session = commandService.handle(command);

        // Add a small delay to ensure different timestamps
        Thread.sleep(10);

        // Act
        session.complete();
        repository.save(session);
        MonitoringSession completedSession = repository.findById(session.getId()).orElse(null);

        // Assert
        assertThat(completedSession).isNotNull();
        assertThat(completedSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.COMPLETED);
        assertThat(completedSession.getEndTime()).isNotNull();
        assertThat(completedSession.getEndTime()).isAfter(completedSession.getStartTime());
    }

    @Test
    @Order(7)
    @DisplayName("Should handle session lifecycle: start -> pause -> resume -> complete")
    @Transactional
    void testCompleteSessionLifecycle() {
        // Arrange
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(456L, 123L);
        MonitoringSession session = commandService.handle(command);

        // Act & Assert - Start
        assertThat(session.getSessionStatus()).isEqualTo(MonitoringSessionStatus.ACTIVE);

        // Act & Assert - Pause
        session.pause();
        repository.save(session);
        assertThat(session.getSessionStatus()).isEqualTo(MonitoringSessionStatus.PAUSED);

        // Act & Assert - Resume
        session.resume();
        repository.save(session);
        assertThat(session.getSessionStatus()).isEqualTo(MonitoringSessionStatus.ACTIVE);

        // Act & Assert - Complete
        session.complete();
        repository.save(session);
        MonitoringSession finalSession = repository.findById(session.getId()).orElse(null);
        
        assertThat(finalSession).isNotNull();
        assertThat(finalSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.COMPLETED);
        assertThat(finalSession.getEndTime()).isNotNull();
    }
}
