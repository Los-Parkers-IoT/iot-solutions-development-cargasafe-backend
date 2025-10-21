package Proyect.IoTParkers.merchants.domain.model.entities;

import Proyect.IoTParkers.merchants.domain.model.aggregates.Merchant;
import Proyect.IoTParkers.merchants.domain.model.valueobjects.UserId;
import Proyect.IoTParkers.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
public class Employee extends AuditableModel {
    @ManyToOne
    @NotNull
    @JoinColumn(name = "merchant_id", referencedColumnName = "id", nullable = false)
    private Merchant merchant;

    @NotNull
    private UserId userId;


    public Employee(Merchant merchant, UserId userId) {
        this.merchant = merchant;
        this.userId = userId;
    }

    // Default constructor for JPA
    protected Employee() {
    }
}
