package Proyect.IoTParkers.subscriptions.application.internal.commandservices;

import Proyect.IoTParkers.subscriptions.domain.model.commands.CreatePaymentCommand;
import Proyect.IoTParkers.subscriptions.domain.model.entities.Payment;
import Proyect.IoTParkers.subscriptions.domain.services.PaymentCommandService;
import Proyect.IoTParkers.subscriptions.infrastructure.persistence.jpa.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;

    public PaymentCommandServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment handle(CreatePaymentCommand command) {

        if (paymentRepository.existsByTransactionId(command.transactionId())) {
            throw new IllegalArgumentException("Payment with this transaction ID already exists.");
        }

        var payment = new Payment(command);

        try {
            paymentRepository.save(payment);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving course: " + e.getMessage());
        }
        return payment;
    }
}
