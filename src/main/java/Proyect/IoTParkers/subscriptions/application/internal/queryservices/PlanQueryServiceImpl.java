package Proyect.IoTParkers.subscriptions.application.internal.queryservices;

import Proyect.IoTParkers.subscriptions.domain.model.entities.Plan;
import Proyect.IoTParkers.subscriptions.domain.model.queries.GetAllPlansQuery;
import Proyect.IoTParkers.subscriptions.domain.model.queries.GetPlanByIdQuery;
import Proyect.IoTParkers.subscriptions.domain.services.PlanQueryService;
import Proyect.IoTParkers.subscriptions.infrastructure.persistence.jpa.repositories.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanQueryServiceImpl implements PlanQueryService {

    private final PlanRepository planRepository;

    public PlanQueryServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public Plan handle(GetPlanByIdQuery query) {
        return planRepository.findById(query.planId())
                .orElseThrow(() -> new IllegalArgumentException("Plan with ID " + query.planId() + " not found."));
    }

    @Override
    public List<Plan> handle(GetAllPlansQuery query) {
        return planRepository.findAll();
    }
}
