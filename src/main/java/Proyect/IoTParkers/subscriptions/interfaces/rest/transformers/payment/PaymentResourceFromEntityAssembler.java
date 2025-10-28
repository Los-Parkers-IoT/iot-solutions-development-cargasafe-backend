package Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.payment;

import Proyect.IoTParkers.subscriptions.domain.model.entities.Payment;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.payment.PaymentResource;

public class PaymentResourceFromEntityAssembler {
    public static PaymentResource toResourceFromEntity(Payment entity) {
        return new PaymentResource(entity.getId(), entity.getUserId(),
                entity.getReceiptUrl(), entity.getTransactionId(), entity.getStatus().name(), entity.getAmount(), entity.getPaymentDate());
    }
}
