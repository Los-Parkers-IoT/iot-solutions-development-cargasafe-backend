package Proyect.IoTParkers.merchants.interfaces.rest.transformers;

import Proyect.IoTParkers.merchants.domain.model.commands.AddEmployeeToMerchantCommand;
import Proyect.IoTParkers.merchants.domain.model.valueobjects.UserId;
import Proyect.IoTParkers.merchants.interfaces.rest.resources.AddEmployeeResource;

public final class AddEmployeeToMerchantCommandFromResourceAssembler {
    public static AddEmployeeToMerchantCommand toCommandFromResource(Long merchantId, AddEmployeeResource resource) {
        return new AddEmployeeToMerchantCommand(merchantId, new UserId(resource.userId()));
    }
}
