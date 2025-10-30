package Proyect.IoTParkers.subscriptions.interfaces.rest.resources.subscription;

import java.util.Date;

public record SubscriptionResource(
        Long id,
        Long userId,
        Long planId,
        String status,
        Date renewal,
        String paymentMethod
) {
}
