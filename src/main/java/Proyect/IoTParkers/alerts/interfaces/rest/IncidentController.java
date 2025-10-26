package Proyect.IoTParkers.alerts.interfaces.rest;

import Proyect.IoTParkers.alerts.domain.model.queries.GetIncidentsByAlertIdQuery;
import Proyect.IoTParkers.alerts.domain.services.IIncidentCommandService;
import Proyect.IoTParkers.alerts.domain.services.IIncidentQueryService;
import Proyect.IoTParkers.alerts.interfaces.rest.resources.CreateIncidentFromAlertResource;
import Proyect.IoTParkers.alerts.interfaces.rest.resources.IncidentResource;
import Proyect.IoTParkers.alerts.interfaces.rest.transformers.CreateIncidentFromAlertCommandFromResourceAssembler;
import Proyect.IoTParkers.alerts.interfaces.rest.transformers.IncidentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incidents")
@Tag(name = "Incidents", description = "Endpoint for managing incidents sources")
public class IncidentController {
    private final IIncidentCommandService incidentCommandService;
    private final IIncidentQueryService incidentQueryService;

    public IncidentController(IIncidentCommandService incidentCommandService, IIncidentQueryService incidentQueryService) {
        this.incidentCommandService = incidentCommandService;
        this.incidentQueryService = incidentQueryService;
    }

    @PostMapping
    public ResponseEntity<IncidentResource> createIncident(@RequestBody CreateIncidentFromAlertResource resource) {
        var command = CreateIncidentFromAlertCommandFromResourceAssembler.toCommandFromResource(resource);
        var incident = incidentCommandService.handle(command);

        if (incident.isEmpty())
            return ResponseEntity.badRequest().build();

        var incidentResource = IncidentResourceFromEntityAssembler.toResourceFromEntity(incident.get());
        return ResponseEntity.ok(incidentResource);
    }

    @GetMapping("/alert/{alertId}")
    public ResponseEntity<List<IncidentResource>> getIncidentsByAlertId(@PathVariable Long alertId) {
        var query = new GetIncidentsByAlertIdQuery(alertId);
        var incidents = incidentQueryService.handle(query);

        var resources = incidents.stream()
                .map(IncidentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

}
