package Proyect.IoTParkers.subscriptions.interfaces.rest.resources.plan;

import java.math.BigDecimal;

public record PlanResource(
        Long id,
        String name,
        String limits,
        BigDecimal price,
        String description
) {
}
