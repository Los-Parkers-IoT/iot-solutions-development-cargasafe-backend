package Proyect.IoTParkers.merchants.domain.services;

import Proyect.IoTParkers.merchants.domain.model.entities.Employee;
import Proyect.IoTParkers.merchants.domain.model.queries.GetAllEmployeesQuery;
import Proyect.IoTParkers.merchants.domain.model.queries.GetEmployeeByIdQuery;
import Proyect.IoTParkers.merchants.domain.model.queries.GetEmployeesByMerchantIdQuery;

import java.util.List;

public interface EmployeeQueryService {
    List<Employee> handle(GetAllEmployeesQuery query);

    Employee handle(GetEmployeeByIdQuery query);

    List<Employee> handle(GetEmployeesByMerchantIdQuery query);
}
