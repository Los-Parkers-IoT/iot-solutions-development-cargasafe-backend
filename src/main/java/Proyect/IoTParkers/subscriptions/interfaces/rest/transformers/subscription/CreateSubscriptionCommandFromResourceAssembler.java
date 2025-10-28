package Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.subscription;

import Proyect.IoTParkers.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.subscription.CreateSubscriptionResource;

public class CreateSubscriptionCommandFromResourceAssembler {
    public static CreateSubscriptionCommand toCommandFromResource(CreateSubscriptionResource resource) {
        return new CreateSubscriptionCommand(resource.userId(),
                resource.planId(), resource.renewal(), resource.paymentMethod());
    }
}
