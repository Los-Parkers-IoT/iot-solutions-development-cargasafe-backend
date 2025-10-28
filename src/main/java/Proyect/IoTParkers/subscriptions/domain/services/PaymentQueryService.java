package Proyect.IoTParkers.subscriptions.domain.services;

import Proyect.IoTParkers.subscriptions.domain.model.entities.Payment;
import Proyect.IoTParkers.subscriptions.domain.model.queries.GetPaymentsByUserIdQuery;

import java.util.List;

public interface PaymentQueryService {
    List<Payment> handle(GetPaymentsByUserIdQuery query);
}
