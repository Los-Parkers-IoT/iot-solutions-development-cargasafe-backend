package Proyect.IoTParkers.merchants.interfaces.rest.transformers;

import Proyect.IoTParkers.merchants.domain.model.commands.CreateMerchantCommand;
import Proyect.IoTParkers.merchants.interfaces.rest.resources.CreateMerchantResource;

public final class CreateMerchantCommandFromResourceAssembler {
    static public CreateMerchantCommand toCommandFromResource(CreateMerchantResource resource) {
        return new CreateMerchantCommand(
                resource.name(),
                resource.contactEmail(),
                resource.fiscalAddress(),
                resource.ruc()
        );
    }
}
