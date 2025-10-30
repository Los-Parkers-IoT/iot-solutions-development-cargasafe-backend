package Proyect.IoTParkers.subscriptions.interfaces.rest.resources.subscription;

import java.util.Date;

public record CreateSubscriptionResource(
        Long userId,
        Long planId,
        Date renewal,
        String paymentMethod
) {
}
