package Proyect.IoTParkers.alerts.application.internal.queryservices;

import Proyect.IoTParkers.alerts.domain.model.entities.Incident;
import Proyect.IoTParkers.alerts.domain.model.queries.GetIncidentsByAlertIdQuery;
import Proyect.IoTParkers.alerts.domain.services.IIncidentQueryService;
import Proyect.IoTParkers.alerts.infrastructure.persistence.jpa.IIncidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentQueryServiceImpl implements IIncidentQueryService {

    private final IIncidentRepository incidentRepository;

    public IncidentQueryServiceImpl(IIncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Override
    public List<Incident> handle(GetIncidentsByAlertIdQuery query) {
        return incidentRepository.findByAlertId(query.alertId());
    }
}
