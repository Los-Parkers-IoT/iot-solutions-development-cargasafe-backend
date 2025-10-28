package Proyect.IoTParkers.subscriptions.domain.model.entities;

import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import Proyect.IoTParkers.subscriptions.domain.model.commands.CreatePlanCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
public class Plan extends AuditableModel {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "limits", nullable = false)
    private String limits;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "description", nullable = false)
    private String description;

    public Plan() {}

    public Plan(CreatePlanCommand command) {
        this.name = command.name();
        this.limits = command.limits();
        this.price = command.price();
        this.description = command.description();
    }
}
