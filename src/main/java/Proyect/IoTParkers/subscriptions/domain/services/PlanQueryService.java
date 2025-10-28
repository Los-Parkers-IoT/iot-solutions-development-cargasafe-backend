package Proyect.IoTParkers.subscriptions.domain.services;

import Proyect.IoTParkers.subscriptions.domain.model.entities.Plan;
import Proyect.IoTParkers.subscriptions.domain.model.queries.GetAllPlansQuery;
import Proyect.IoTParkers.subscriptions.domain.model.queries.GetPlanByIdQuery;

import java.util.List;

public interface PlanQueryService {
    Plan handle(GetPlanByIdQuery query);

    List<Plan> handle(GetAllPlansQuery query);
}
