package Proyect.IoTParkers.subscriptions.application.internal.queryservices;

import Proyect.IoTParkers.subscriptions.domain.model.aggregates.Subscription;
import Proyect.IoTParkers.subscriptions.domain.model.queries.GetSubscriptionByUserIdQuery;
import Proyect.IoTParkers.subscriptions.domain.services.SubscriptionQueryService;
import Proyect.IoTParkers.subscriptions.infrastructure.persistence.jpa.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Subscription handle(GetSubscriptionByUserIdQuery query) {
        return subscriptionRepository.findByUserId(query.userId())
                .orElseThrow(() -> new RuntimeException("Subscription not found for userId: " + query.userId()));
    }
}
