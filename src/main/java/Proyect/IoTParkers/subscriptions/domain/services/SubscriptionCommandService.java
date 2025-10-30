package Proyect.IoTParkers.subscriptions.domain.services;

import Proyect.IoTParkers.subscriptions.domain.model.aggregates.Subscription;
import Proyect.IoTParkers.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import Proyect.IoTParkers.subscriptions.domain.model.commands.DeleteSubscriptionCommand;
import Proyect.IoTParkers.subscriptions.domain.model.commands.ChangePlanCommand;

public interface SubscriptionCommandService {
    Subscription handle(CreateSubscriptionCommand command);
    void handle(DeleteSubscriptionCommand command);

    Subscription handle(ChangePlanCommand command);
}
