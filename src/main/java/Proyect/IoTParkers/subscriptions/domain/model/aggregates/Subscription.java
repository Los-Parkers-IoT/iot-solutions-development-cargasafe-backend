package Proyect.IoTParkers.subscriptions.domain.model.aggregates;

import Proyect.IoTParkers.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import Proyect.IoTParkers.subscriptions.domain.model.commands.CreateSubscriptionCommand;
import Proyect.IoTParkers.subscriptions.domain.model.entities.Plan;
import Proyect.IoTParkers.subscriptions.domain.model.valueobjects.PlanStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@Entity
public class Subscription extends AuditableAbstractAggregateRoot<Subscription> {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PlanStatus status;

    @Column(name = "renewal", nullable = false)
    private Date renewal;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    public Subscription() {
    }

    public Subscription(CreateSubscriptionCommand command, Plan plan) {
        this.userId = command.userId();
        this.plan = plan;
        this.status = PlanStatus.ACTIVE;
        this.renewal = command.renewal();
        this.paymentMethod = command.paymentMethod();
    }

}

