package Proyect.IoTParkers.subscriptions.domain.services;

import Proyect.IoTParkers.subscriptions.domain.model.commands.CreatePaymentCommand;
import Proyect.IoTParkers.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import Proyect.IoTParkers.subscriptions.domain.model.entities.Payment;

import java.util.UUID;

public interface PaymentCommandService {
    Payment handle(CreatePaymentCommand command);
}
