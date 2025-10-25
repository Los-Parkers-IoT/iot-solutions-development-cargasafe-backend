package Proyect.IoTParkers.alerts.application.internal.queryservices;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAlertByIdQuery;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAlertsByStatusQuery;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAlertsByTypeQuery;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAllAlertsQuery;
import Proyect.IoTParkers.alerts.domain.services.IAlertQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertQueryServiceImpl implements IAlertQueryService {
    @Override
    public Optional<Alert> handle(GetAlertByIdQuery query) {
        return Optional.empty();
    }

    @Override
    public List<Alert> handle(GetAllAlertsQuery query) {
        return List.of();
    }

    @Override
    public List<Alert> handle(GetAlertsByTypeQuery query) {
        return List.of();
    }

    @Override
    public List<Alert> handle(GetAlertsByStatusQuery query) {
        return List.of();
    }
}
