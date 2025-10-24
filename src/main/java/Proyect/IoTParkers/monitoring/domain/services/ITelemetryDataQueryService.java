package Proyect.IoTParkers.monitoring.domain.services;

import Proyect.IoTParkers.monitoring.domain.model.entities.TelemetryData;
import Proyect.IoTParkers.monitoring.domain.model.queries.GetTelemetryDataBySessionQuery;

import java.util.Optional;

public interface ITelemetryDataQueryService {
    Optional<TelemetryData> handle(GetTelemetryDataBySessionQuery query);
}
