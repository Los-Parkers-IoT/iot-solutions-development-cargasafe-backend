package Proyect.IoTParkers.subscriptions.interfaces.rest.resources.subscription;

import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.plan.PlanResource;

import java.util.Date;

public record GetSubscriptionByUserResource(
        Long id,
        Long userId,
        String status,
        Date renewal,
        String paymentMethod,
        PlanResource plan
) {
}
