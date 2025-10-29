package Proyect.IoTParkers.monitoring.interfaces.rest;

import Proyect.IoTParkers.monitoring.application.internal.commandservices.MonitoringCommandServiceImpl;
import Proyect.IoTParkers.monitoring.application.internal.queryservices.MonitoringQueryServiceImpl;
import Proyect.IoTParkers.monitoring.domain.model.commands.EndMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.commands.PauseMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.commands.ResumeMonitoringSessionCommand;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetActiveSessionQuery;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetMonitoringSessionByIdQuery;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetSessionsByTripIdQuery;
import Proyect.IoTParkers.monitoring.domain.services.IMonitoringSessionCommandService;
import Proyect.IoTParkers.monitoring.domain.services.IMonitoringSessionQueryService;
import Proyect.IoTParkers.monitoring.interfaces.rest.resources.monitoring.MonitoringSessionResource;
import Proyect.IoTParkers.monitoring.interfaces.rest.resources.monitoring.StartMonitoringSessionResource;
import Proyect.IoTParkers.monitoring.interfaces.rest.transformers.monitoring.MonitoringSessionResourceFromEntityAssembler;
import Proyect.IoTParkers.monitoring.interfaces.rest.transformers.monitoring.StartMonitoringSessionCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/monitoring", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Monitoring", description = "Monitoring Operations Endpoint")
public class MonitoringController {

    private final IMonitoringSessionCommandService monitoringCommandService;
    private final IMonitoringSessionQueryService monitoringQueryService;

    public MonitoringController(
            IMonitoringSessionCommandService monitoringCommandService,
            IMonitoringSessionQueryService monitoringQueryService) {
        this.monitoringCommandService = monitoringCommandService;
        this.monitoringQueryService = monitoringQueryService;
    }

    @PostMapping("/sessions")
    public ResponseEntity<MonitoringSessionResource> startSession(@RequestBody StartMonitoringSessionResource resource) {
        var command = StartMonitoringSessionCommandFromResourceAssembler.toCommandFromResource(resource);
        var session = monitoringCommandService.handle(command);

        if (session == null)
            return ResponseEntity.badRequest().build();

        var sessionResource = MonitoringSessionResourceFromEntityAssembler.toResourceFromEntity(session);
        return new ResponseEntity<>(sessionResource, HttpStatus.CREATED);
    }

    @PostMapping("/sessions/{sessionId}/pause")
    public ResponseEntity<MonitoringSessionResource> pauseSession(@PathVariable Long sessionId) {
        var command = new PauseMonitoringSessionCommand(sessionId);
        var session = monitoringCommandService.handle(command);

        var sessionResource = MonitoringSessionResourceFromEntityAssembler.toResourceFromEntity(session);
        return ResponseEntity.ok(sessionResource);
    }

    @PostMapping("/sessions/{sessionId}/end")
    public ResponseEntity<MonitoringSessionResource> endSession(@PathVariable Long sessionId) {
        var command = new EndMonitoringSessionCommand(sessionId);
        var session = monitoringCommandService.handle(command);

        var sessionResource = MonitoringSessionResourceFromEntityAssembler.toResourceFromEntity(session);
        return ResponseEntity.ok(sessionResource);
    }

    @PostMapping("/sessions/{sessionId}/resume")
    public ResponseEntity<MonitoringSessionResource> resumeSession(@PathVariable Long sessionId) {
        var command = new ResumeMonitoringSessionCommand(sessionId);
        var session = monitoringCommandService.handle(command);

        var sessionResource = MonitoringSessionResourceFromEntityAssembler.toResourceFromEntity(session);
        return ResponseEntity.ok(sessionResource);
    }

    @GetMapping("/sessions/active")
    public ResponseEntity<List<MonitoringSessionResource>> getActiveSessions() {
        var sessions = monitoringQueryService.handle(new GetActiveSessionQuery());

        var sessionResources = sessions.stream()
                .map(MonitoringSessionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(sessionResources);
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<MonitoringSessionResource> getSessionById(@PathVariable Long sessionId) {
        var session = monitoringQueryService.handle(new GetMonitoringSessionByIdQuery(sessionId));

        var sessionResource = MonitoringSessionResourceFromEntityAssembler.toResourceFromEntity(session);
        return ResponseEntity.ok(sessionResource);
    }

    @GetMapping("/sessions/trip/{tripId}")
    public ResponseEntity<MonitoringSessionResource> getSessionByTripId(@PathVariable Long tripId) {
        var session = monitoringQueryService.handle(new GetSessionsByTripIdQuery(tripId));

        var sessionResource = MonitoringSessionResourceFromEntityAssembler.toResourceFromEntity(session);
        return ResponseEntity.ok(sessionResource);
    }
}
