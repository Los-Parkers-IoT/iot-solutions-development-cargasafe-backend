package Proyect.IoTParkers.subscriptions.interfaces.rest.resources.payment;

import java.math.BigDecimal;
import java.util.Date;

public record PaymentResource(
        Long id,
        Long userId,
        String receiptUrl,
        String transactionId,
        String status,
        BigDecimal amount,
        Date paymentDate
) {
}
