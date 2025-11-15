package Proyect.IoTParkers.monitoring.integration;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.commands.AddTelemetryDataCommand;
import Proyect.IoTParkers.monitoring.domain.model.commands.StartMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetTelemetryDataBySessionQuery;
import Proyect.IoTParkers.monitoring.domain.services.IMonitoringSessionCommandService;
import Proyect.IoTParkers.monitoring.domain.services.ITelemetryDataCommandService;
import Proyect.IoTParkers.monitoring.domain.services.ITelemetryDataQueryService;
import Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa.IMonitoringSessionRepository;
import Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa.ITelemetryDataRepository;
import Proyect.IoTParkers.trip.interfaces.acl.TripContextFacade;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.Collections;

/**
 * Integration Tests for Telemetry Data Service
 * Tests the complete flow of telemetry data capture and retrieval
 */
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("TelemetryData Service - Integration Tests")
class TelemetryDataServiceIntegrationTest {

    @Autowired
    private ITelemetryDataCommandService telemetryCommandService;

    @Autowired
    private ITelemetryDataQueryService telemetryQueryService;

    @Autowired
    private IMonitoringSessionCommandService sessionCommandService;

    @Autowired
    private ITelemetryDataRepository telemetryRepository;

    @Autowired
    private IMonitoringSessionRepository sessionRepository;

    @MockBean
    private TripContextFacade tripContextFacade;

    private MonitoringSession testSession;

    @BeforeEach
    void setUp() {
        telemetryRepository.deleteAll();
        sessionRepository.deleteAll();

        // Mock trip validation - return empty list (no threshold violations)
        when(tripContextFacade.validateTripThresholds(anyLong(), anyDouble(), anyDouble()))
                .thenReturn(Collections.emptyList());

        // Create a test monitoring session
        StartMonitoringSessionCommand sessionCommand = new StartMonitoringSessionCommand(456L, 123L);
        testSession = sessionCommandService.handle(sessionCommand);
    }

    @Test
    @Order(1)
    @DisplayName("Should add telemetry data to an active monitoring session")
    @Transactional
    void testAddTelemetryData() {
        // Arrange
        AddTelemetryDataCommand command = new AddTelemetryDataCommand(
                testSession.getId(),
                25.5f,
                60.0f,
                0.2f,
                -12.0464f,
                -77.0428f
        );

        // Act
        TelemetryData telemetry = telemetryCommandService.handle(command);

        // Assert
        assertThat(telemetry).isNotNull();
        assertThat(telemetry.getId()).isNotNull();
        assertThat(telemetry.getTemperature()).isEqualTo(25.5f);
        assertThat(telemetry.getHumidity()).isEqualTo(60.0f);
        assertThat(telemetry.getVibration()).isEqualTo(0.2f);
        assertThat(telemetry.getSession().getId()).isEqualTo(testSession.getId());
    }

    @Test
    @Order(2)
    @DisplayName("Should retrieve telemetry data by session ID")
    @Transactional
    void testGetTelemetryDataBySession() {
        // Arrange
        AddTelemetryDataCommand command1 = new AddTelemetryDataCommand(
                testSession.getId(), 25.5f, 60.0f, 0.2f, -12.0464f, -77.0428f
        );
        AddTelemetryDataCommand command2 = new AddTelemetryDataCommand(
                testSession.getId(), 26.0f, 61.0f, 0.3f, -12.0465f, -77.0429f
        );

        telemetryCommandService.handle(command1);
        telemetryCommandService.handle(command2);

        // Act
        GetTelemetryDataBySessionQuery query = new GetTelemetryDataBySessionQuery(testSession.getId());
        List<TelemetryData> telemetryList = telemetryQueryService.handle(query);

        // Assert
        assertThat(telemetryList).isNotEmpty();
        assertThat(telemetryList).hasSize(2);
        // Verify both temperature values are present (order may vary)
        assertThat(telemetryList)
                .extracting(TelemetryData::getTemperature)
                .containsExactlyInAnyOrder(25.5f, 26.0f);
    }

    @Test
    @Order(3)
    @DisplayName("Should persist multiple telemetry entries with different values")
    @Transactional
    void testPersistMultipleTelemetryEntries() {
        // Arrange & Act
        for (int i = 0; i < 5; i++) {
            AddTelemetryDataCommand command = new AddTelemetryDataCommand(
                    testSession.getId(),
                    25.0f + i,
                    60.0f + i,
                    0.1f + (i * 0.1f),
                    -12.0464f - (i * 0.0001f),
                    -77.0428f - (i * 0.0001f)
            );
            telemetryCommandService.handle(command);
        }

        // Assert
        GetTelemetryDataBySessionQuery query = new GetTelemetryDataBySessionQuery(testSession.getId());
        List<TelemetryData> telemetryList = telemetryQueryService.handle(query);

        assertThat(telemetryList).hasSize(5);
        // Verify all 5 temperature values are present (order may vary)
        assertThat(telemetryList)
                .extracting(TelemetryData::getTemperature)
                .containsExactlyInAnyOrder(25.0f, 26.0f, 27.0f, 28.0f, 29.0f);
    }

    @Test
    @Order(4)
    @DisplayName("Should handle extreme temperature values")
    @Transactional
    void testExtremeTemperatureValues() {
        // Arrange
        AddTelemetryDataCommand coldCommand = new AddTelemetryDataCommand(
                testSession.getId(), -40.0f, 50.0f, 0.1f, -12.0464f, -77.0428f
        );
        AddTelemetryDataCommand hotCommand = new AddTelemetryDataCommand(
                testSession.getId(), 60.0f, 50.0f, 0.1f, -12.0464f, -77.0428f
        );

        // Act
        TelemetryData coldData = telemetryCommandService.handle(coldCommand);
        TelemetryData hotData = telemetryCommandService.handle(hotCommand);

        // Assert
        assertThat(coldData.getTemperature()).isEqualTo(-40.0f);
        assertThat(hotData.getTemperature()).isEqualTo(60.0f);
    }

    @Test
    @Order(5)
    @DisplayName("Should handle high vibration readings")
    @Transactional
    void testHighVibrationReadings() {
        // Arrange
        AddTelemetryDataCommand command = new AddTelemetryDataCommand(
                testSession.getId(), 25.0f, 60.0f, 5.0f, -12.0464f, -77.0428f
        );

        // Act
        TelemetryData telemetry = telemetryCommandService.handle(command);

        // Assert
        assertThat(telemetry.getVibration()).isEqualTo(5.0f);
    }

    @Test
    @Order(6)
    @DisplayName("Should track GPS coordinates accurately")
    @Transactional
    void testGPSCoordinateTracking() {
        // Arrange - Different locations in Lima
        AddTelemetryDataCommand miraflores = new AddTelemetryDataCommand(
                testSession.getId(), 25.0f, 60.0f, 0.1f, -12.1192f, -77.0350f
        );
        AddTelemetryDataCommand callao = new AddTelemetryDataCommand(
                testSession.getId(), 26.0f, 61.0f, 0.2f, -12.0566f, -77.1181f
        );

        // Act
        TelemetryData data1 = telemetryCommandService.handle(miraflores);
        TelemetryData data2 = telemetryCommandService.handle(callao);

        // Assert
        assertThat(data1.getLatitude()).isEqualTo(-12.1192f);
        assertThat(data1.getLongitude()).isEqualTo(-77.0350f);
        assertThat(data2.getLatitude()).isEqualTo(-12.0566f);
        assertThat(data2.getLongitude()).isEqualTo(-77.1181f);
    }

    @Test
    @Order(7)
    @DisplayName("Should maintain telemetry data order by creation time")
    @Transactional
    void testTelemetryDataOrdering() throws InterruptedException {
        // Arrange & Act
        AddTelemetryDataCommand command1 = new AddTelemetryDataCommand(
                testSession.getId(), 25.0f, 60.0f, 0.1f, -12.0464f, -77.0428f
        );
        TelemetryData data1 = telemetryCommandService.handle(command1);

        Thread.sleep(100); // Small delay to ensure different timestamps

        AddTelemetryDataCommand command2 = new AddTelemetryDataCommand(
                testSession.getId(), 26.0f, 61.0f, 0.2f, -12.0465f, -77.0429f
        );
        TelemetryData data2 = telemetryCommandService.handle(command2);

        // Assert
        assertThat(data1.getCreatedAt()).isBefore(data2.getCreatedAt());
    }

    @Test
    @Order(8)
    @DisplayName("Should return empty list for session with no telemetry data")
    @Transactional
    void testEmptyTelemetryList() {
        // Arrange
        StartMonitoringSessionCommand newSessionCommand = new StartMonitoringSessionCommand(888L, 999L);
        MonitoringSession newSession = sessionCommandService.handle(newSessionCommand);

        // Act
        GetTelemetryDataBySessionQuery query = new GetTelemetryDataBySessionQuery(newSession.getId());
        List<TelemetryData> telemetryList = telemetryQueryService.handle(query);

        // Assert
        assertThat(telemetryList).isEmpty();
    }
}
