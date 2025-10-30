package Proyect.IoTParkers.subscriptions.interfaces.rest;

import Proyect.IoTParkers.subscriptions.domain.model.queries.GetAllPlansQuery;
import Proyect.IoTParkers.subscriptions.domain.services.PlanCommandService;
import Proyect.IoTParkers.subscriptions.domain.services.PlanQueryService;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.plan.CreatePlanResource;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.plan.PlanResource;
import Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.plan.CreatePlanCommandFromResourceAssembler;
import Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.plan.PlanResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/plans", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Plan", description = "Plan Management Endpoints")
public class PlanController {

    private final PlanCommandService planCommandService;
    private final PlanQueryService planQueryService;

    public PlanController(PlanCommandService planCommandService, PlanQueryService planQueryService) {
        this.planCommandService = planCommandService;
        this.planQueryService = planQueryService;
    }

    @PostMapping
    public ResponseEntity<PlanResource> createPlan(@RequestBody CreatePlanResource resource) {

        var createPlanCommand = CreatePlanCommandFromResourceAssembler
                .toCommandFromResource(resource);
        var plan = planCommandService.handle(createPlanCommand);
        if (plan == null)
            return ResponseEntity.badRequest().build();

        var planResource = PlanResourceFromEntityAssembler.toResourceFromEntity(plan);
        return new ResponseEntity<>(planResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PlanResource>> getAllPlans() {

        var planList = planQueryService.handle(new GetAllPlansQuery());

        var planResourceList = planList.stream()
                .map(PlanResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(planResourceList);
    }

}
