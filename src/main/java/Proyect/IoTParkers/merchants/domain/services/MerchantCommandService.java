package Proyect.IoTParkers.merchants.domain.services;

import Proyect.IoTParkers.merchants.domain.model.aggregates.Merchant;
import Proyect.IoTParkers.merchants.domain.model.commands.CreateMerchantCommand;

import java.util.Optional;

public interface MerchantCommandService {
    Optional<Merchant> handle(CreateMerchantCommand command);
}
