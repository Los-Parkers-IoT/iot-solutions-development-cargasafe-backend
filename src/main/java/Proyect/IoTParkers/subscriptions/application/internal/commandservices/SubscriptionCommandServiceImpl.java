package Proyect.IoTParkers.subscriptions.application.internal.commandservices;

import Proyect.IoTParkers.subscriptions.domain.model.aggregates.Subscription;
import Proyect.IoTParkers.subscriptions.domain.model.commands.ChangePlanCommand;
import Proyect.IoTParkers.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import Proyect.IoTParkers.subscriptions.domain.model.commands.DeleteSubscriptionCommand;
import Proyect.IoTParkers.subscriptions.domain.services.SubscriptionCommandService;
import Proyect.IoTParkers.subscriptions.infrastructure.persistence.jpa.repositories.PlanRepository;
import Proyect.IoTParkers.subscriptions.infrastructure.persistence.jpa.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository, PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    @Override
    public Subscription handle(CreateSubscriptionCommand command) {

        if (subscriptionRepository.existsByUserId(command.userId())) {
            throw new IllegalArgumentException("User already has a subscription.");
        }

        var plan = planRepository.findById(command.planId())
                .orElseThrow(() -> new IllegalArgumentException("Plan not found."));

        var subscription = new Subscription(command, plan);

        try {
            subscriptionRepository.save(subscription);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving course: " + e.getMessage());
        }
        return subscription;
    }

    @Override
    public void handle(DeleteSubscriptionCommand command) {
        subscriptionRepository.findById(command.subscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found."));

        subscriptionRepository.deleteById(command.subscriptionId());
    }

    @Override
    public Subscription handle(ChangePlanCommand command) {

        var subscription = subscriptionRepository.findById(command.subscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found with id " + command.subscriptionId()));

        var newPlan = planRepository.findById(command.newPlanId())
                .orElseThrow(() -> new IllegalArgumentException("Plan not found with id " + command.newPlanId()));

        subscription.changePlan(newPlan);

        var updated = subscriptionRepository.save(subscription);

        return updated;
    }}
