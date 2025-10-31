package Proyect.IoTParkers.monitoring.application.internal.queryservices;


import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetTelemetryDataBySessionQuery;
import Proyect.IoTParkers.monitoring.domain.services.ITelemetryDataQueryService;
import Proyect.IoTParkers.monitoring.infrastructure.persistence.jpa.ITelemetryDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelemetryQueryServiceImpl implements ITelemetryDataQueryService {

    private final ITelemetryDataRepository telemetryDataRepository;

    public TelemetryQueryServiceImpl(ITelemetryDataRepository telemetryDataRepository) {
        this.telemetryDataRepository = telemetryDataRepository;
    }

    @Override
    public List<TelemetryData> handle(GetTelemetryDataBySessionQuery query) {
        return telemetryDataRepository.findAllBySessionId(query.sessionId());
    }
}
