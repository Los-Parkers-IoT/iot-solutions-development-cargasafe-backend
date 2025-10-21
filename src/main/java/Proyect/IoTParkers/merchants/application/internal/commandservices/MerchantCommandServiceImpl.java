package Proyect.IoTParkers.merchants.application.internal.commandservices;

import Proyect.IoTParkers.merchants.domain.model.aggregates.Merchant;
import Proyect.IoTParkers.merchants.domain.model.commands.CreateMerchantCommand;
import Proyect.IoTParkers.merchants.domain.services.MerchantCommandService;
import Proyect.IoTParkers.merchants.infrastructure.persistence.jpa.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantCommandServiceImpl implements MerchantCommandService {
    private final MerchantRepository merchantRepository;

    public MerchantCommandServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public Optional<Merchant> handle(CreateMerchantCommand command) {
        if (merchantRepository.existsByRuc(command.ruc())) {
            throw new IllegalArgumentException("Merchant with ruc " + command.ruc() + " already exists");
        }

        var merchant = new Merchant(command);
        var createdMerchant = merchantRepository.save(merchant);

        return Optional.of(createdMerchant);
    }
}
