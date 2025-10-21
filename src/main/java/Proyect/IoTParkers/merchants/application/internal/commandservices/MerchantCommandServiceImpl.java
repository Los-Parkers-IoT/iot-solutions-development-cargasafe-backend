package Proyect.IoTParkers.merchants.application.internal.commandservices;

import Proyect.IoTParkers.merchants.domain.exceptions.MerchantRucAlreadyExistsException;
import Proyect.IoTParkers.merchants.domain.model.aggregates.Merchant;
import Proyect.IoTParkers.merchants.domain.model.commands.CreateMerchantCommand;
import Proyect.IoTParkers.merchants.domain.services.MerchantCommandService;
import Proyect.IoTParkers.merchants.infrastructure.persistence.jpa.MerchantRepository;
import org.springframework.stereotype.Service;

@Service
public class MerchantCommandServiceImpl implements MerchantCommandService {
    private final MerchantRepository merchantRepository;

    public MerchantCommandServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public Merchant handle(CreateMerchantCommand command) {
        if (merchantRepository.existsByRuc(command.ruc())) {
            throw new MerchantRucAlreadyExistsException(command.ruc());
        }
        var merchant = new Merchant(command);

        return merchantRepository.save(merchant);
    }
}
