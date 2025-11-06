package Proyect.IoTParkers.monitoring.interfaces.rest;

import Proyect.IoTParkers.monitoring.application.internal.queryservices.TelemetryQueryServiceImpl;
import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetTelemetryDataBySessionQuery;
import Proyect.IoTParkers.monitoring.domain.services.ITelemetryDataCommandService;
import Proyect.IoTParkers.monitoring.domain.services.ITelemetryDataQueryService;
import Proyect.IoTParkers.monitoring.interfaces.rest.resources.telemetry.AddTelemetryDataResource;
import Proyect.IoTParkers.monitoring.interfaces.rest.resources.telemetry.TelemetryDataResource;
import Proyect.IoTParkers.monitoring.interfaces.rest.transformers.telemetry.AddTelemetryDataCommandFromResourceAssembler;
import Proyect.IoTParkers.monitoring.interfaces.rest.transformers.telemetry.TelemetryDataResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/telemetry", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Telemetry", description = "Telemetry Operations Endpoint")
public class TelemetryController {

    private final ITelemetryDataCommandService telemetryCommandService;
    private final ITelemetryDataQueryService telemetryQueryService;

    public TelemetryController(
            ITelemetryDataCommandService telemetryCommandService,
            ITelemetryDataQueryService telemetryQueryService) {
        this.telemetryCommandService = telemetryCommandService;
        this.telemetryQueryService = telemetryQueryService;
    }

    @PostMapping
    public ResponseEntity<TelemetryDataResource> addTelemetryData(@RequestBody AddTelemetryDataResource resource) {
        var command = AddTelemetryDataCommandFromResourceAssembler.toCommandFromResource(resource);
        var telemetry = telemetryCommandService.handle(command);

        if (telemetry == null)
            return ResponseEntity.badRequest().build();

        var telemetryResource = TelemetryDataResourceFromEntityAssembler.toResourceFromEntity(telemetry);
        return new ResponseEntity<>(telemetryResource, HttpStatus.CREATED);
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<TelemetryDataResource>> getTelemetryBySession(@PathVariable Long sessionId) {
        var telemetryList = telemetryQueryService.handle(new GetTelemetryDataBySessionQuery(sessionId));

        var telemetryResources = telemetryList.stream()
                .map(TelemetryDataResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(telemetryResources);
    }
}
