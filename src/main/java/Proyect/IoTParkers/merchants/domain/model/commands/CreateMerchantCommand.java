package Proyect.IoTParkers.merchants.domain.model.commands;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record CreateMerchantCommand(String name, String contactEmail, String fiscalAddress, String ruc) {

    public CreateMerchantCommand {
        name = Preconditions.requireNonBlank(name, "Name");
        contactEmail = Preconditions.requireNonBlank(contactEmail, "Contact email");
        fiscalAddress = Preconditions.requireNonBlank(fiscalAddress, "Fiscal address");
        ruc = Preconditions.requireNonBlank(ruc, "RUC");
    }
}
