package Proyect.IoTParkers.subscriptions.domain.model.entities;

import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import Proyect.IoTParkers.subscriptions.domain.model.commands.CreatePaymentCommand;
import Proyect.IoTParkers.subscriptions.domain.model.valueobjects.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Getter
@Entity
public class Payment extends AuditableModel {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "receipt_url", nullable = false)
    private String receiptUrl;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private Date paymentDate;

    public Payment() {
    }

    public Payment(CreatePaymentCommand command) {
        this.userId = command.userId();
        this.receiptUrl = command.receiptUrl();
        this.transactionId = command.transactionId();
        this.status = PaymentStatus.SUCCEEDED;
        this.amount = command.amount();
        this.paymentDate = Date.from(Instant.now());
    }
}
