package Proyect.IoTParkers.subscriptions.domain.services;

import Proyect.IoTParkers.subscriptions.domain.model.aggregates.Subscription;
import Proyect.IoTParkers.subscriptions.domain.model.queries.GetSubscriptionByUserIdQuery;

public interface SubscriptionQueryService {
    Subscription handle(GetSubscriptionByUserIdQuery query);
}
