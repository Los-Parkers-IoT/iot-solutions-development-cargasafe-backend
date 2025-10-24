package Proyect.IoTParkers.monitoring.interfaces.rest;

import Proyect.IoTParkers.monitoring.application.internal.queryservices.TelemetryQueryServiceImpl;
import Proyect.IoTParkers.monitoring.domain.model.aggregates.MonitoringSession;
import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/telemetry")
@Tag(name = "Telemetry", description = "Telemetry Operations Endpoint")
public class TelemetryController {
    private final TelemetryQueryServiceImpl telemetryQueryService;

    public TelemetryController(TelemetryQueryServiceImpl telemetryQueryService) {
        this.telemetryQueryService = telemetryQueryService;
    }


}
