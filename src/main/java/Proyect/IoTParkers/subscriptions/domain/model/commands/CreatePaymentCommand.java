package Proyect.IoTParkers.subscriptions.domain.model.commands;

import java.math.BigDecimal;

public record CreatePaymentCommand(
        Long userId,
        String receiptUrl,
        String transactionId,
        BigDecimal amount
) {
}
