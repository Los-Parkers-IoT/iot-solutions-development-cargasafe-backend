package Proyect.IoTParkers.monitoring.interfaces.rest;

import Proyect.IoTParkers.monitoring.application.internal.commandservices.MonitoringCommandServiceImpl;
import Proyect.IoTParkers.monitoring.application.internal.queryservices.MonitoringQueryServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/monitoring")
@Tag(name = "Monitoring", description = "Monitoring Operations Endpoint")
public class MonitoringController {
    private final MonitoringCommandServiceImpl monitoringCommandService;
    private final MonitoringQueryServiceImpl monitoringQueryService;

    public MonitoringController(MonitoringCommandServiceImpl monitoringCommandService, MonitoringQueryServiceImpl monitoringQueryService) {
        this.monitoringCommandService = monitoringCommandService;
        this.monitoringQueryService = monitoringQueryService;
    }


}
