package Proyect.IoTParkers.subscriptions.infrastructure.persistence.jpa.repositories;

import Proyect.IoTParkers.subscriptions.domain.model.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByTransactionId(String transactionId);
    List<Payment> findAllByUserId(Long userId);
}
