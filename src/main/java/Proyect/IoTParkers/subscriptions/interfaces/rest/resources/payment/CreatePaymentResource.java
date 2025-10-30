package Proyect.IoTParkers.subscriptions.interfaces.rest.resources.payment;

import java.math.BigDecimal;

public record CreatePaymentResource(
        Long userId,
        String receiptUrl,
        String transactionId,
        BigDecimal amount
) {
}
