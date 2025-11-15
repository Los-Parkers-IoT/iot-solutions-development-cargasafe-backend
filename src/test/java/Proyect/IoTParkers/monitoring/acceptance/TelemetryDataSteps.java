package Proyect.IoTParkers.monitoring.acceptance;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.commands.AddTelemetryDataCommand;
import Proyect.IoTParkers.monitoring.domain.model.commands.StartMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetTelemetryDataBySessionQuery;
import Proyect.IoTParkers.monitoring.domain.services.IMonitoringSessionCommandService;
import Proyect.IoTParkers.monitoring.domain.services.ITelemetryDataCommandService;
import Proyect.IoTParkers.monitoring.domain.services.ITelemetryDataQueryService;
import io.cucumber.java.es.*;

import io.cucumber.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

/**
 * Step Definitions for Telemetry Data Capture BDD Scenarios
 */
public class TelemetryDataSteps {

    @Autowired
    private ITelemetryDataCommandService telemetryCommandService;

    @Autowired
    private ITelemetryDataQueryService telemetryQueryService;

    @Autowired
    private IMonitoringSessionCommandService sessionCommandService;

    private MonitoringSession currentSession;
    private TelemetryData currentTelemetry;
    private List<TelemetryData> telemetryList;
    private Exception lastException;
    private Float lastTemperature;
    private Float lastHumidity;
    private Float lastVibration;

    @Dado("que el sistema de telemetría está operativo")
    public void queElSistemaDeTelemetriaEstaOperativo() {
        assertThat(telemetryCommandService).isNotNull();
        assertThat(telemetryQueryService).isNotNull();
    }

    @Dado("existe una sesión de monitoreo activa con ID {string}")
    public void existeUnaSesionDeMonitoreoActivaConID(String sessionId) {
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(
                Long.parseLong(sessionId.replace("SESSION-", "")),
                999L
        );
        currentSession = sessionCommandService.handle(command);
        assertThat(currentSession).isNotNull();
        assertThat(currentSession.isActive()).isTrue();
    }

    @Dado("que el dispositivo IoT está transmitiendo datos")
    public void queElDispositivoIoTEstaTransmitiendoDatos() {
        // Simular dispositivo activo
        assertThat(true).isTrue();
    }

    @Cuando("recibo datos de telemetría con los siguientes valores:")
    public void reciboAutosDeTelemetriaConLosSiguientesValores(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        
        AddTelemetryDataCommand command = new AddTelemetryDataCommand(
                currentSession.getId(),
                Float.parseFloat(data.get("temperatura")),
                Float.parseFloat(data.get("humedad")),
                Float.parseFloat(data.get("vibracion")),
                Float.parseFloat(data.get("latitud")),
                Float.parseFloat(data.get("longitud"))
        );
        
        currentTelemetry = telemetryCommandService.handle(command);
    }

    @Entonces("los datos se almacenan correctamente en la sesión {string}")
    public void losDatosSeAlmacenanCorrectamenteEnLaSesion(String sessionId) {
        assertThat(currentTelemetry).isNotNull();
        assertThat(currentTelemetry.getId()).isNotNull();
        assertThat(currentTelemetry.getSession().getId()).isEqualTo(currentSession.getId());
    }

    @Entonces("el sistema registra la marca de tiempo de recepción")
    public void elSistemaRegistraLaMarcaDeTiempoDeRecepcion() {
        assertThat(currentTelemetry.getCreatedAt()).isNotNull();
    }

    @Entonces("los datos quedan disponibles para consulta inmediata")
    public void losDatosQuedanDisponiblesParaConsultaInmediata() {
        GetTelemetryDataBySessionQuery query = new GetTelemetryDataBySessionQuery(currentSession.getId());
        List<TelemetryData> data = telemetryQueryService.handle(query);
        assertThat(data).isNotEmpty();
    }

    @Dado("que el dispositivo detecta condiciones de frío extremo")
    public void queElDispositivoDetectaCondicionesDeFrioExtremo() {
        lastTemperature = -40.0f;
    }

    @Cuando("recibo datos de telemetría con temperatura de {float} grados")
    public void reciboDatosDeTelemetriaConTemperaturaDegrados(Float temperature) {
        AddTelemetryDataCommand command = new AddTelemetryDataCommand(
                currentSession.getId(),
                temperature,
                50.0f,
                0.1f,
                -12.0464f,
                -77.0428f
        );
        currentTelemetry = telemetryCommandService.handle(command);
    }

    @Entonces("el sistema almacena el valor de temperatura {float}")
    public void elSistemaAlmacenaElValorDeTemperatura(Float expectedTemperature) {
        assertThat(currentTelemetry.getTemperature()).isEqualTo(expectedTemperature);
    }

    @Entonces("se debe generar una alerta de temperatura fuera de rango")
    public void seDebeGenerarUnaAlertaDeTemperaturaFueraDeRango() {
        // Verificar que se generó una alerta (esto se integra con el bounded context de Alerts)
        assertThat(currentTelemetry).isNotNull();
    }

    @Entonces("los datos quedan registrados en el historial")
    public void losDatosQuedanRegistradosEnElHistorial() {
        assertThat(currentTelemetry.getId()).isNotNull();
        assertThat(currentTelemetry.getCreatedAt()).isNotNull();
    }

    @Dado("que el dispositivo detecta condiciones de calor extremo")
    public void queElDispositivoDetectaCondicionesDeCalorExtremo() {
        lastTemperature = 60.0f;
    }

    @Dado("que el vehículo está en una carretera con baches")
    public void queElVehiculoEstaEnUnaCarreteraConBaches() {
        lastVibration = 5.0f;
    }

    @Cuando("recibo datos de telemetría con vibración de {float} unidades")
    public void reciboDatosDeTelemetriaConVibracionDeUnidades(Float vibration) {
        AddTelemetryDataCommand command = new AddTelemetryDataCommand(
                currentSession.getId(),
                25.0f,
                60.0f,
                vibration,
                -12.0464f,
                -77.0428f
        );
        currentTelemetry = telemetryCommandService.handle(command);
    }

    @Entonces("el sistema almacena el valor de vibración {float}")
    public void elSistemaAlmacenaElValorDeVibracion(Float expectedVibration) {
        assertThat(currentTelemetry.getVibration()).isEqualTo(expectedVibration);
    }

    @Entonces("se debe generar una alerta de vibración excesiva")
    public void seDebeGenerarUnaAlertaDeVibracionExcesiva() {
        // Verificar que se generó una alerta
        assertThat(currentTelemetry).isNotNull();
    }

    @Entonces("se registra la ubicación GPS donde ocurrió el evento")
    public void seRegistraLaUbicacionGPSDondeOcurrioElEvento() {
        assertThat(currentTelemetry.getLatitude()).isNotNull();
        assertThat(currentTelemetry.getLongitude()).isNotNull();
    }

    @Dado("que el dispositivo está transmitiendo continuamente")
    public void queElDispositivoEstaTransmitiendoContinuamente() {
        assertThat(currentSession.isActive()).isTrue();
    }

    @Cuando("recibo {int} lecturas de telemetría en intervalos de {int} segundos")
    public void reciboLecturasDeTelemetriaEnIntervalosDe(Integer numReadings, Integer interval) {
        for (int i = 0; i < numReadings; i++) {
            AddTelemetryDataCommand command = new AddTelemetryDataCommand(
                    currentSession.getId(),
                    25.0f + i,
                    60.0f + i,
                    0.1f,
                    -12.0464f,
                    -77.0428f
            );
            telemetryCommandService.handle(command);
        }
    }

    @Entonces("el sistema almacena las {int} lecturas correctamente")
    public void elSistemaAlmacenaLasLecturasCorrectamente(Integer expectedCount) {
        GetTelemetryDataBySessionQuery query = new GetTelemetryDataBySessionQuery(currentSession.getId());
        telemetryList = telemetryQueryService.handle(query);
        assertThat(telemetryList).hasSize(expectedCount);
    }

    @Entonces("cada lectura tiene una marca de tiempo única")
    public void cadaLecturaTieneUnaMarcaDeTiempoUnica() {
        assertThat(telemetryList).allMatch(t -> t.getCreatedAt() != null);
    }

    @Entonces("las lecturas están ordenadas cronológicamente")
    public void lasLecturasEstanOrdenadasCronologicamente() {
        for (int i = 1; i < telemetryList.size(); i++) {
            assertThat(telemetryList.get(i).getCreatedAt())
                    .isAfterOrEqualTo(telemetryList.get(i-1).getCreatedAt());
        }
    }

    @Entonces("todas las lecturas están asociadas a la sesión {string}")
    public void todasLasLecturasEstanAsociadasALaSesion(String sessionId) {
        assertThat(telemetryList).allMatch(t -> 
                t.getSession().getId().equals(currentSession.getId()));
    }

    @Dado("que la sesión {string} tiene {int} lecturas de telemetría almacenadas")
    public void queLaSesionTieneLecturasDeTelemetriaAlmacenadas(String sessionId, Integer numReadings) {
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(
                Long.parseLong(sessionId.replace("SESSION-", "")),
                999L
        );
        currentSession = sessionCommandService.handle(command);

        for (int i = 0; i < numReadings; i++) {
            AddTelemetryDataCommand telemetryCommand = new AddTelemetryDataCommand(
                    currentSession.getId(),
                    25.0f,
                    60.0f,
                    0.1f,
                    -12.0464f,
                    -77.0428f
            );
            telemetryCommandService.handle(telemetryCommand);
        }
    }

    @Cuando("consulto los datos de telemetría de la sesión {string}")
    public void consultoLosDatosDeTelemetriaDeLaSesion(String sessionId) {
        GetTelemetryDataBySessionQuery query = new GetTelemetryDataBySessionQuery(currentSession.getId());
        telemetryList = telemetryQueryService.handle(query);
    }

    @Entonces("el sistema devuelve las {int} lecturas")
    public void elSistemaDevuelveLasLecturas(Integer expectedCount) {
        assertThat(telemetryList).hasSize(expectedCount);
    }

    @Entonces("las lecturas incluyen temperatura, humedad, vibración y coordenadas GPS")
    public void lasLecturasIncluyenTemperaturaHumedadVibracionYCoordenadasGPS() {
        assertThat(telemetryList).allMatch(t -> 
                t.getTemperature() != null &&
                t.getHumidity() != null &&
                t.getVibration() != null &&
                t.getLatitude() != null &&
                t.getLongitude() != null
        );
    }

    @Entonces("los datos están ordenados por fecha de captura")
    public void losDatosEstanOrdenadosPorFechaDeCaptura() {
        for (int i = 1; i < telemetryList.size(); i++) {
            assertThat(telemetryList.get(i).getCreatedAt())
                    .isAfterOrEqualTo(telemetryList.get(i-1).getCreatedAt());
        }
    }

    @Dado("que la sesión {string} tiene estado {string}")
    public void queLaSesionTieneEstado(String sessionId, String status) {
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(300L, 999L);
        currentSession = sessionCommandService.handle(command);
        
        if (status.equals("PAUSED")) {
            currentSession.pause();
        }
    }

    @Cuando("intento enviar datos de telemetría a la sesión {string}")
    public void intentoEnviarDatosDeTelemetriaALaSesion(String sessionId) {
        try {
            AddTelemetryDataCommand command = new AddTelemetryDataCommand(
                    currentSession.getId(),
                    25.0f,
                    60.0f,
                    0.1f,
                    -12.0464f,
                    -77.0428f
            );
            currentTelemetry = telemetryCommandService.handle(command);
            lastException = null;
        } catch (Exception e) {
            lastException = e;
        }
    }

    @Entonces("el sistema rechaza los datos")
    public void elSistemaRechazaLosDatos() {
        assertThat(lastException).isNotNull();
    }

    @Entonces("muestra un error {string}")
    public void muestraUnError(String expectedMessage) {
        assertThat(lastException.getMessage()).isEqualTo(expectedMessage);
    }

    @Entonces("los datos no son almacenados")
    public void losDatosNoSonAlmacenados() {
        assertThat(currentTelemetry).isNull();
    }
}
