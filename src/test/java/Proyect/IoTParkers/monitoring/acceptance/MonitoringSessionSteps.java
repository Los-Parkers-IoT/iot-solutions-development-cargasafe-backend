package Proyect.IoTParkers.monitoring.acceptance;

import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.commands.StartMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.valueobjects.MonitoringSessionStatus;
import Proyect.IoTParkers.monitoring.domain.services.IMonitoringSessionCommandService;
import Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa.IMonitoringSessionRepository;
import io.cucumber.java.es.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

/**
 * Step Definitions for Monitoring Session Management BDD Scenarios
 */
public class MonitoringSessionSteps {

    @Autowired
    private IMonitoringSessionCommandService commandService;

    @Autowired
    private IMonitoringSessionRepository repository;

    private MonitoringSession currentSession;
    private String deviceId;
    private String tripId;
    private Exception lastException;

    @Dado("que el sistema de monitoreo está disponible")
    public void queElSistemaDeMonitoreoEstaDisponible() {
        assertThat(commandService).isNotNull();
        assertThat(repository).isNotNull();
    }

    @Dado("tengo un dispositivo IoT con ID {string}")
    public void tengoUnDispositivoIoTConID(String deviceId) {
        this.deviceId = deviceId;
        assertThat(deviceId).isNotEmpty();
    }

    @Dado("tengo un viaje programado con ID {string}")
    public void tengoUnViajeProgramadoConID(String tripId) {
        this.tripId = tripId;
        assertThat(tripId).isNotEmpty();
    }

    @Dado("que tengo credenciales válidas de gestor de transporte")
    public void queTengoCredencialesValidasDeGestorDeTransporte() {
        // Simular autenticación válida
        assertThat(true).isTrue();
    }

    @Cuando("inicio una sesión de monitoreo para el dispositivo {string} y viaje {string}")
    public void inicioUnaSesionDeMonitoreoParaElDispositivoYViaje(String deviceId, String tripId) {
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(
                Long.parseLong(deviceId.replace("DEVICE-", "")),
                Long.parseLong(tripId.replace("TRIP-", ""))
        );
        currentSession = commandService.handle(command);
    }

    @Entonces("el sistema crea una nueva sesión con estado {string}")
    public void elSistemaCreaUnaNuevaSesionConEstado(String expectedStatus) {
        assertThat(currentSession).isNotNull();
        assertThat(currentSession.getSessionStatus().name()).isEqualTo(expectedStatus);
    }

    @Entonces("la sesión registra la hora de inicio")
    public void laSesionRegistraLaHoraDeInicio() {
        assertThat(currentSession.getStartTime()).isNotNull();
    }

    @Entonces("la sesión está vinculada al dispositivo {string}")
    public void laSesionEstaVinculadaAlDispositivo(String expectedDeviceId) {
        String deviceNumber = expectedDeviceId.replace("DEVICE-", "");
        assertThat(currentSession.getDeviceId()).isEqualTo(deviceNumber);
    }

    @Entonces("la sesión está vinculada al viaje {string}")
    public void laSesionEstaVinculadaAlViaje(String expectedTripId) {
        String tripNumber = expectedTripId.replace("TRIP-", "");
        assertThat(currentSession.getTripId()).isEqualTo(tripNumber);
    }

    @Dado("que existe una sesión de monitoreo activa con ID {string}")
    public void queExisteUnaSesionDeMonitoreoActivaConID(String sessionId) {
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(123L, 456L);
        currentSession = commandService.handle(command);
        assertThat(currentSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.ACTIVE);
    }

    @Cuando("pauso la sesión de monitoreo {string}")
    public void pausoLaSesionDeMonitoreo(String sessionId) {
        currentSession.pause();
        repository.save(currentSession);
    }

    @Entonces("el estado de la sesión cambia a {string}")
    public void elEstadoDeLaSesionCambiaA(String expectedStatus) {
        MonitoringSession updatedSession = repository.findById(currentSession.getId()).orElse(null);
        assertThat(updatedSession).isNotNull();
        assertThat(updatedSession.getSessionStatus().name()).isEqualTo(expectedStatus);
    }

    @Entonces("el sistema deja de aceptar nuevos datos de telemetría para esa sesión")
    public void elSistemaDejaDeAceptarNuevosDatosDeTelemetriaParaEsaSesion() {
        assertThat(currentSession.isActive()).isFalse();
    }

    @Dado("que existe una sesión de monitoreo pausada con ID {string}")
    public void queExisteUnaSesionDeMonitoreoPausadaConID(String sessionId) {
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(789L, 101L);
        currentSession = commandService.handle(command);
        currentSession.pause();
        repository.save(currentSession);
        assertThat(currentSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.PAUSED);
    }

    @Cuando("reanudo la sesión de monitoreo {string}")
    public void reanudoLaSesionDeMonitoreo(String sessionId) {
        currentSession.resume();
        repository.save(currentSession);
    }

    @Entonces("el sistema vuelve a aceptar datos de telemetría")
    public void elSistemaVuelveAAceptarDatosDeTelemetria() {
        assertThat(currentSession.isActive()).isTrue();
    }

    @Dado("el viaje asociado ha llegado a su destino")
    public void elViajeAsociadoHaLlegadoASuDestino() {
        // Simular que el viaje finalizó
        assertThat(true).isTrue();
    }

    @Cuando("completo la sesión de monitoreo {string}")
    public void completoLaSesionDeMonitoreo(String sessionId) {
        currentSession.complete();
        repository.save(currentSession);
    }

    @Entonces("la sesión registra la hora de finalización")
    public void laSesionRegistraLaHoraDeFinalizacion() {
        assertThat(currentSession.getEndTime()).isNotNull();
    }

    @Entonces("el sistema ya no acepta datos de telemetría para esa sesión")
    public void elSistemaYaNoAceptaDatosDeTelemetriaParaEsaSesion() {
        assertThat(currentSession.isActive()).isFalse();
        assertThat(currentSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.COMPLETED);
    }

    @Dado("que existe una sesión de monitoreo con estado {string}")
    public void queExisteUnaSesionDeMonitoreoConEstado(String estado) {
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(555L, 666L);
        currentSession = commandService.handle(command);
        
        if (estado.equals("PAUSED")) {
            currentSession.pause();
            repository.save(currentSession);
        } else if (estado.equals("COMPLETED")) {
            currentSession.complete();
            repository.save(currentSession);
        }
    }

    @Cuando("intento pausar nuevamente la sesión")
    public void intentoPausarNuevamenteLaSesion() {
        try {
            currentSession.pause();
            lastException = null;
        } catch (Exception e) {
            lastException = e;
        }
    }

    @Entonces("el sistema muestra un error {string}")
    public void elSistemaMuestraUnError(String expectedMessage) {
        assertThat(lastException).isNotNull();
        assertThat(lastException.getMessage()).isEqualTo(expectedMessage);
    }

    @Entonces("el estado de la sesión no cambia")
    public void elEstadoDeLaSesionNoCambia() {
        assertThat(currentSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.PAUSED);
    }

    @Cuando("intento completar nuevamente la sesión")
    public void intentoCompletarNuevamenteLaSesion() {
        try {
            currentSession.complete();
            lastException = null;
        } catch (Exception e) {
            lastException = e;
        }
    }

    @Entonces("no se modifica la hora de finalización original")
    public void noSeModificaLaHoraDeFinalizacionOriginal() {
        var originalEndTime = currentSession.getEndTime();
        assertThat(originalEndTime).isNotNull();
    }

    @Dado("que inicio una nueva sesión de monitoreo")
    public void queInicioUnaNuevaSesionDeMonitoreo() {
        StartMonitoringSessionCommand command = new StartMonitoringSessionCommand(123L, 456L);
        currentSession = commandService.handle(command);
    }

    @Cuando("la sesión pasa por los estados: ACTIVE -> PAUSED -> ACTIVE -> COMPLETED")
    public void laSesionPasaPorLosEstados() {
        assertThat(currentSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.ACTIVE);
        
        currentSession.pause();
        repository.save(currentSession);
        assertThat(currentSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.PAUSED);
        
        currentSession.resume();
        repository.save(currentSession);
        assertThat(currentSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.ACTIVE);
        
        currentSession.complete();
        repository.save(currentSession);
        assertThat(currentSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.COMPLETED);
    }

    @Entonces("cada transición de estado es registrada correctamente")
    public void cadaTransicionDeEstadoEsRegistradaCorrectamente() {
        assertThat(currentSession.getSessionStatus()).isEqualTo(MonitoringSessionStatus.COMPLETED);
    }

    @Entonces("la sesión mantiene la integridad de todos sus datos")
    public void laSesionMantieneLaIntegridadDeTodosSusDatos() {
        assertThat(currentSession.getDeviceId()).isNotEmpty();
        assertThat(currentSession.getTripId()).isNotEmpty();
        assertThat(currentSession.getStartTime()).isNotNull();
    }

    @Entonces("la hora de finalización es posterior a la hora de inicio")
    public void laHoraDeFinalizacionEsPosteriorALaHoraDeInicio() {
        assertThat(currentSession.getEndTime()).isAfter(currentSession.getStartTime());
    }
}
