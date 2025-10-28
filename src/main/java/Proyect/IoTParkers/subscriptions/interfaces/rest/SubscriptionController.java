package Proyect.IoTParkers.subscriptions.interfaces.rest;

import Proyect.IoTParkers.subscriptions.domain.model.commands.DeleteSubscriptionCommand;
import Proyect.IoTParkers.subscriptions.domain.model.queries.GetPlanByIdQuery;
import Proyect.IoTParkers.subscriptions.domain.model.queries.GetSubscriptionByUserIdQuery;
import Proyect.IoTParkers.subscriptions.domain.services.PlanQueryService;
import Proyect.IoTParkers.subscriptions.domain.services.SubscriptionCommandService;
import Proyect.IoTParkers.subscriptions.domain.services.SubscriptionQueryService;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.subscription.CreateSubscriptionResource;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.subscription.GetSubscriptionByUserResource;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.subscription.SubscriptionResource;
import Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.plan.PlanResourceFromEntityAssembler;
import Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.subscription.CreateSubscriptionCommandFromResourceAssembler;
import Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.subscription.SubscriptionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/subscription", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Subscription", description = "Subscription Management Endpoints")
public class SubscriptionController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    private final PlanQueryService planQueryService;

    public SubscriptionController(SubscriptionCommandService subscriptionCommandService, SubscriptionQueryService subscriptionQueryService, PlanQueryService planQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
        this.planQueryService = planQueryService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionResource> createPlan(@RequestBody CreateSubscriptionResource resource) {

        var createSubscriptionCommand = CreateSubscriptionCommandFromResourceAssembler
                .toCommandFromResource(resource);

        var subscription = subscriptionCommandService.handle(createSubscriptionCommand);
        if (subscription == null)
            return ResponseEntity.badRequest().build();

        var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription);
        return new ResponseEntity<>(subscriptionResource, HttpStatus.CREATED);
    }

    @GetMapping("/user-id/{userId}")
    public ResponseEntity<GetSubscriptionByUserResource> getSubscriptionByUserId(@PathVariable Long userId) {
        var getSubscriptionByUserIdQuery = new GetSubscriptionByUserIdQuery(userId);
        var subscription = subscriptionQueryService.handle(getSubscriptionByUserIdQuery);

        if (subscription == null)
            return ResponseEntity.badRequest().build();

        var plan = planQueryService.handle(new GetPlanByIdQuery(subscription.getPlan().getId()));

        var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription);
        var planResource = PlanResourceFromEntityAssembler.toResourceFromEntity(plan);

        return ResponseEntity.ok(new GetSubscriptionByUserResource(
                subscriptionResource.id(),
                subscriptionResource.userId(),
                subscriptionResource.status(),
                subscriptionResource.renewal(),
                subscriptionResource.paymentMethod(),
                planResource
        ));
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<?> deleteSubscription(@PathVariable Long subscriptionId) {
        var deleteSubscriptionCommand = new DeleteSubscriptionCommand(subscriptionId);
        subscriptionCommandService.handle(deleteSubscriptionCommand);
        return ResponseEntity.noContent()
                .build();
    }

}
