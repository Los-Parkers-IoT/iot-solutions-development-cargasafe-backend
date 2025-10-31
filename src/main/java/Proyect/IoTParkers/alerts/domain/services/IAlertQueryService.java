package Proyect.IoTParkers.alerts.domain.services;

import Proyect.IoTParkers.alerts.domain.model.aggregates.Alert;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAlertByIdQuery;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAlertsByStatusQuery;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAlertsByTypeQuery;
import Proyect.IoTParkers.alerts.domain.model.queries.GetAllAlertsQuery;

import java.util.List;
import java.util.Optional;

public interface IAlertQueryService {

    Optional<Alert> handle(GetAlertByIdQuery query);

    List<Alert> handle(GetAllAlertsQuery query);

    List<Alert> handle(GetAlertsByTypeQuery query);

    List<Alert> handle(GetAlertsByStatusQuery query);
}
