package Proyect.IoTParkers.subscriptions.domain.model.commands;

import java.util.Date;

public record CreateSubscriptionCommand(
        Long userId,
        Long planId,
        Date renewal,
        String paymentMethod
) {
}
