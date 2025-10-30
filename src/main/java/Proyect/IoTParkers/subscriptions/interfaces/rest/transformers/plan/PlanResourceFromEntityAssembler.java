package Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.plan;

import Proyect.IoTParkers.subscriptions.domain.model.entities.Plan;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.plan.PlanResource;

public class PlanResourceFromEntityAssembler {
    public static PlanResource toResourceFromEntity(Plan entity) {
        return new PlanResource(entity.getId(), entity.getName(),
                entity.getLimits(), entity.getPrice(), entity.getDescription());
    }
}
