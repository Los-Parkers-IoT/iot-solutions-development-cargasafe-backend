package Proyect.IoTParkers.merchants.domain.model.aggregates;

import Proyect.IoTParkers.merchants.domain.model.commands.CreateMerchantCommand;
import Proyect.IoTParkers.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
@Entity
public class Merchant extends AuditableAbstractAggregateRoot<Merchant> {
    private final String name;
    private final String contactEmail;
    private final String fiscalAddress;
    private final String ruc;
    private final boolean isActive;

    public Merchant() {
        this.name = Strings.EMPTY;
        this.contactEmail = Strings.EMPTY;
        this.fiscalAddress = Strings.EMPTY;
        this.ruc = Strings.EMPTY;
        this.isActive = false;
    }

    public Merchant(CreateMerchantCommand command) {
        this.name = command.name();
        this.contactEmail = command.contactEmail();
        this.fiscalAddress = command.fiscalAddress();
        this.ruc = command.ruc();
        this.isActive = false;
    }
}
