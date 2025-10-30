package Proyect.IoTParkers.subscriptions.interfaces.rest.resources.plan;

import java.math.BigDecimal;

public record CreatePlanResource(
        String name,
        String limits,
        BigDecimal price,
        String description
) {
}
