package Proyect.IoTParkers.merchants.interfaces.rest.transformers;

import Proyect.IoTParkers.merchants.domain.model.entities.Employee;
import Proyect.IoTParkers.merchants.interfaces.rest.resources.EmployeeResource;

public final class EmployeeResourceFromEntityAssembler {

    public static EmployeeResource toResourceFromEntity(Employee entity) {
        return new EmployeeResource(entity.getId(), entity.getMerchant().getId(), entity.getUserId().asLong());
    }
}
