package Proyect.IoTParkers.subscriptions.infrastructure.persistence.jpa.repositories;

import Proyect.IoTParkers.subscriptions.domain.model.aggregates.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByUserId(Long userId);
    Optional<Subscription> findByUserId(Long userId);
}
