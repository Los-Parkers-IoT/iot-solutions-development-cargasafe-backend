package Proyect.IoTParkers.alerts.interfaces.rest;

import Proyect.IoTParkers.alerts.domain.model.queries.GetNotificationsByAlertIdQuery;
import Proyect.IoTParkers.alerts.domain.services.INotificationQueryService;
import Proyect.IoTParkers.alerts.interfaces.rest.resources.NotificationResource;
import Proyect.IoTParkers.alerts.interfaces.rest.transformers.NotificationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notifications", description = "Endpoint for managing notifications sources")
public class NotificationController {

    private final INotificationQueryService notificationQueryService;

    public NotificationController(INotificationQueryService notificationQueryService) {
        this.notificationQueryService = notificationQueryService;
    }

    @GetMapping("/alert/{alertId}")
    public ResponseEntity<List<NotificationResource>> getNotificationsByAlertId(@PathVariable Long alertId) {
        var query = new GetNotificationsByAlertIdQuery(alertId);
        var notifications = notificationQueryService.handle(query);

        var resources = notifications.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

}
