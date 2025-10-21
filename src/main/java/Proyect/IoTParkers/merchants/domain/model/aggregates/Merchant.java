package Proyect.IoTParkers.merchants.domain.model.aggregates;

import Proyect.IoTParkers.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
@Entity
public class Merchant extends AuditableAbstractAggregateRoot<Merchant> {
    private String name;
    private String contactEmail;
    private String fiscalAddress;
    private String ruc;
    private boolean isActive;


    public Merchant() {
        this.name = Strings.EMPTY;
        this.contactEmail = Strings.EMPTY;
        this.fiscalAddress = Strings.EMPTY;
        this.ruc = Strings.EMPTY;
        this.isActive = false;
    }
}
