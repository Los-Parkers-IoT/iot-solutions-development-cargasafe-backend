package Proyect.IoTParkers.merchants.application.internal.queryservices;

import Proyect.IoTParkers.merchants.domain.exceptions.EmployeeNotFoundException;
import Proyect.IoTParkers.merchants.domain.model.entities.Employee;
import Proyect.IoTParkers.merchants.domain.model.queries.GetAllEmployeesQuery;
import Proyect.IoTParkers.merchants.domain.model.queries.GetEmployeeByIdQuery;
import Proyect.IoTParkers.merchants.domain.model.queries.GetEmployeesByMerchantIdQuery;
import Proyect.IoTParkers.merchants.domain.services.EmployeeQueryService;
import Proyect.IoTParkers.merchants.infrastructure.persistence.jpa.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeQueryServiceImpl implements EmployeeQueryService {
    private final EmployeeRepository employeeRepository;


    public EmployeeQueryServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public List<Employee> handle(GetAllEmployeesQuery query) {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee handle(GetEmployeeByIdQuery query) {
        return this.employeeRepository.findById(query.id()).orElseThrow(() -> new EmployeeNotFoundException(query.id()));
    }

    @Override
    public List<Employee> handle(GetEmployeesByMerchantIdQuery query) {

        return this.employeeRepository.findByMerchantId(query.id());
    }
}
