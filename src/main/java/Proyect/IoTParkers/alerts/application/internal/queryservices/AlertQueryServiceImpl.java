package Proyect.IoTParkers.alerts.application.internal.queryservices;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAlertByIdQuery;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAlertsByStatusQuery;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAlertsByTypeQuery;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAllAlertsQuery;
import Proyect.IoTParkers.alerts.domain.services.IAlertQueryService;
import Proyect.IoTParkers.alerts.infrastructure.persistence.jpa.IAlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertQueryServiceImpl implements IAlertQueryService {

    private final IAlertRepository alertRepository;

    public AlertQueryServiceImpl(IAlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Optional<Alert> handle(GetAlertByIdQuery query) {
        return alertRepository.findById(query.alertId());
    }

    @Override
    public List<Alert> handle(GetAllAlertsQuery query) {
        return alertRepository.findAll();
    }

    @Override
    public List<Alert> handle(GetAlertsByTypeQuery query) {
        return alertRepository.findByAlertType(query.alertType)
    }

    @Override
    public List<Alert> handle(GetAlertsByStatusQuery query) {
        return alertRepository.findByAlertStatusId(query.alertStatusId());
    }
}
