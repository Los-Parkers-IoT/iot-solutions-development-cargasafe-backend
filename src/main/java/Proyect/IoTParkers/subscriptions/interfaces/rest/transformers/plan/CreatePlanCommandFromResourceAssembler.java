package Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.plan;

import Proyect.IoTParkers.subscriptions.domain.model.commands.CreatePlanCommand;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.plan.CreatePlanResource;

public class CreatePlanCommandFromResourceAssembler {
    public static CreatePlanCommand toCommandFromResource(CreatePlanResource resource) {
        return new CreatePlanCommand(resource.name(), resource.limits(),
                resource.price(), resource.description());
    }
}
