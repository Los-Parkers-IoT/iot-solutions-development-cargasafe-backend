package Proyect.IoTParkers.subscriptions.application.internal.queryservices;

import Proyect.IoTParkers.subscriptions.domain.model.entities.Payment;
import Proyect.IoTParkers.subscriptions.domain.model.queries.GetPaymentsByUserIdQuery;
import Proyect.IoTParkers.subscriptions.domain.services.PaymentQueryService;
import Proyect.IoTParkers.subscriptions.infrastructure.persistence.jpa.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentRepository paymentRepository;

    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> handle(GetPaymentsByUserIdQuery query) {
        return paymentRepository.findAllByUserId(query.userId());
    }
}
