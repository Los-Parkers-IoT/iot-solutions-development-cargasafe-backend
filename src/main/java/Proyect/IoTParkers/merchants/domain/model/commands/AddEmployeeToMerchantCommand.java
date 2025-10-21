package Proyect.IoTParkers.merchants.domain.model.commands;

import Proyect.IoTParkers.merchants.domain.model.valueobjects.UserId;

public record AddEmployeeToMerchantCommand(Long merchantId, UserId userId) {
}
