package Proyect.IoTParkers.subscriptions.domain.services;

import Proyect.IoTParkers.subscriptions.domain.model.commands.CreatePlanCommand;
import Proyect.IoTParkers.subscriptions.domain.model.entities.Plan;

import java.util.UUID;

public interface PlanCommandService {
    Plan handle(CreatePlanCommand command);
}
