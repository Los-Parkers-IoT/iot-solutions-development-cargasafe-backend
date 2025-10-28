package Proyect.IoTParkers.subscriptions.domain.services;

import Proyect.IoTParkers.subscriptions.domain.model.aggregates.Subscription;
import Proyect.IoTParkers.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import Proyect.IoTParkers.subscriptions.domain.model.commands.DeleteSubscriptionCommand;

public interface SubscriptionCommandService {
    Subscription handle(CreateSubscriptionCommand command);
    void handle(DeleteSubscriptionCommand command);
}
