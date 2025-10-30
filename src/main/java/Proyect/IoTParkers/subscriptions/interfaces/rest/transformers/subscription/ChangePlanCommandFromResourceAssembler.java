package Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.subscription;

import Proyect.IoTParkers.subscriptions.domain.model.commands.ChangePlanCommand;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.subscription.ChangePlanResource;

public class ChangePlanCommandFromResourceAssembler {

    public static ChangePlanCommand toCommandFromResource(Long subscriptionId, ChangePlanResource resource) {
        return new ChangePlanCommand(subscriptionId, resource.newPlanId());
    }

}
