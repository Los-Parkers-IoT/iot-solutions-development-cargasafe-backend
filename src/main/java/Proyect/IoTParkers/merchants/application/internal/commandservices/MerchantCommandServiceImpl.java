package Proyect.IoTParkers.merchants.application.internal.commandservices;

import Proyect.IoTParkers.merchants.domain.exceptions.MerchantNotFoundException;
import Proyect.IoTParkers.merchants.domain.exceptions.MerchantRucAlreadyExistsException;
import Proyect.IoTParkers.merchants.domain.model.aggregates.Merchant;
import Proyect.IoTParkers.merchants.domain.model.commands.AddEmployeeToMerchantCommand;
import Proyect.IoTParkers.merchants.domain.model.commands.CreateMerchantCommand;
import Proyect.IoTParkers.merchants.domain.model.entities.Employee;
import Proyect.IoTParkers.merchants.domain.services.MerchantCommandService;
import Proyect.IoTParkers.merchants.infrastructure.persistence.jpa.EmployeeRepository;
import Proyect.IoTParkers.merchants.infrastructure.persistence.jpa.MerchantRepository;
import org.springframework.stereotype.Service;

@Service
public class MerchantCommandServiceImpl implements MerchantCommandService {
    private final MerchantRepository merchantRepository;
    private final EmployeeRepository employeeRepository;

    public MerchantCommandServiceImpl(MerchantRepository merchantRepository, EmployeeRepository employeeRepository) {
        this.merchantRepository = merchantRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Merchant handle(CreateMerchantCommand command) {
        if (merchantRepository.existsByRuc(command.ruc())) {
            throw new MerchantRucAlreadyExistsException(command.ruc());
        }
        var merchant = new Merchant(command);

        return merchantRepository.save(merchant);
    }

    @Override
    public void handle(AddEmployeeToMerchantCommand command) {
        var merchant = merchantRepository.findById(command.merchantId()).orElseThrow(() -> new MerchantNotFoundException(command.merchantId()));
        var employee = new Employee(merchant, command.userId());

        employeeRepository.save(employee);
    }
}
