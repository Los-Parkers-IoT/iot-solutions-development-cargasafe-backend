package Proyect.IoTParkers.subscriptions.domain.model.commands;

import java.math.BigDecimal;

public record CreatePlanCommand(
        String name,
        String limits,
        BigDecimal price,
        String description
) {
}
