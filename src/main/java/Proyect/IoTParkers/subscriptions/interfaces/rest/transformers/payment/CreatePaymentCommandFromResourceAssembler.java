package Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.payment;

import Proyect.IoTParkers.subscriptions.domain.model.commands.CreatePaymentCommand;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.payment.CreatePaymentResource;

public class CreatePaymentCommandFromResourceAssembler {
    public static CreatePaymentCommand toCommandFromResource(CreatePaymentResource resource) {
        return new CreatePaymentCommand(resource.userId(), resource.receiptUrl(),
                resource.transactionId(), resource.amount());
    }
}
