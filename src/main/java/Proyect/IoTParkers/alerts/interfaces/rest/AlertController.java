package Proyect.IoTParkers.alerts.interfaces.rest;

import Proyect.IoTParkers.alerts.domain.model.commands.CreateAlertCommand;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAlertByIdQuery;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAlertsByStatusQuery;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAlertsByTypeQuery;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAllAlertsQuery;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertStatus;
import Proyect.IoTParkers.alerts.domain.model.valueobjects.AlertType;
import Proyect.IoTParkers.alerts.domain.services.IAlertCommandService;
import Proyect.IoTParkers.alerts.domain.services.IAlertQueryService;
import Proyect.IoTParkers.alerts.interfaces.rest.resources.AlertResource;
import Proyect.IoTParkers.alerts.interfaces.rest.resources.CreateAlertResource;
import Proyect.IoTParkers.alerts.interfaces.rest.transformers.AcknowledgeAlertCommandFromResourceAssembler;
import Proyect.IoTParkers.alerts.interfaces.rest.transformers.AlertResourceFromEntityAssembler;
import Proyect.IoTParkers.alerts.interfaces.rest.transformers.CloseAlertCommandFromResourceAssembler;
import Proyect.IoTParkers.alerts.interfaces.rest.transformers.CreateAlertCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alerts")
@Tag(name = "Alerts", description = "Endpoint for managing alerts sources")
public class AlertController {

    private final IAlertCommandService alertCommandService;
    private final IAlertQueryService alertQueryService;

    public AlertController(IAlertCommandService alertCommandService, IAlertQueryService alertQueryService) {
        this.alertCommandService = alertCommandService;
        this.alertQueryService = alertQueryService;
    }

    @PostMapping
    public ResponseEntity<AlertResource> createAlert(@RequestBody CreateAlertResource resource) {
        CreateAlertCommand command = CreateAlertCommandFromResourceAssembler.toCommandFromResource(resource);

        var alert = alertCommandService.handle(command);

        if (alert.isEmpty())
            return ResponseEntity.badRequest().build();

        var alertResource = AlertResourceFromEntityAssembler.toResourceFromEntity(alert.get());
        return ResponseEntity.ok(alertResource);

    }

    @PatchMapping("/{alertId}/acknowledge")
    public ResponseEntity<AlertResource> acknowledgeAlert(@PathVariable Long alertId) {

        var command = AcknowledgeAlertCommandFromResourceAssembler.toCommandFromResource(alertId);
        var updatedAlert = alertCommandService.handle(command);

        if (updatedAlert.isEmpty())
            return ResponseEntity.notFound().build();

        var alertResource = AlertResourceFromEntityAssembler.toResourceFromEntity(updatedAlert.get());
        return ResponseEntity.ok(alertResource);
    }

    @PatchMapping("/{alertId}/close")
    public ResponseEntity<AlertResource> closeAlert(@PathVariable Long alertId) {
        var command = CloseAlertCommandFromResourceAssembler.toCommandFromResource(alertId);
        var updatedAlert = alertCommandService.handle(command);

        if (updatedAlert.isEmpty())
            return ResponseEntity.notFound().build();

        var alertResource = AlertResourceFromEntityAssembler.toResourceFromEntity(updatedAlert.get());
        return ResponseEntity.ok(alertResource);
    }

    @GetMapping
    public ResponseEntity<List<AlertResource>> getAllAlerts() {
        var query = new GetAllAlertsQuery();
        var alerts = alertQueryService.handle(query);

        var resources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{alertId}")
    public ResponseEntity<AlertResource> getAlertById(@PathVariable Long alertId) {
        var query = new GetAlertByIdQuery(alertId);
        var alert = alertQueryService.handle(query);

        if (alert.isEmpty())
            return ResponseEntity.notFound().build();

        var alertResource = AlertResourceFromEntityAssembler.toResourceFromEntity(alert.get());
        return ResponseEntity.ok(alertResource);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<AlertResource>> getAlertsByType(@PathVariable String type) {
        var alertType = AlertType.valueOf(type.toUpperCase());
        var query = new GetAlertsByTypeQuery(alertType);
        var alerts = alertQueryService.handle(query);

        var resources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AlertResource>> getAlertsByStatus(@PathVariable String status) {
        var alertStatus = AlertStatus.valueOf(status.toUpperCase());
        var query = new GetAlertsByStatusQuery(alertStatus);
        var alerts = alertQueryService.handle(query);

        var resources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

}
