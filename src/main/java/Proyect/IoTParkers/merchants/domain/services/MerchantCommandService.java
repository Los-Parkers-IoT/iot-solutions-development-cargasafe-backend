package Proyect.IoTParkers.merchants.domain.services;

import Proyect.IoTParkers.merchants.domain.model.aggregates.Merchant;
import Proyect.IoTParkers.merchants.domain.model.commands.AddEmployeeToMerchantCommand;
import Proyect.IoTParkers.merchants.domain.model.commands.CreateMerchantCommand;

public interface MerchantCommandService {
    Merchant handle(CreateMerchantCommand command);

    void handle(AddEmployeeToMerchantCommand command);
}
