package Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.subscription;

import Proyect.IoTParkers.subscriptions.domain.model.aggregates.Subscription;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.subscription.SubscriptionResource;

public class SubscriptionResourceFromEntityAssembler {
    public static SubscriptionResource toResourceFromEntity(Subscription entity) {
        return new SubscriptionResource(entity.getId(), entity.getUserId(), entity.getPlan().getId(), entity.getStatus().name(), entity.getRenewal(), entity.getPaymentMethod());
    }
}
