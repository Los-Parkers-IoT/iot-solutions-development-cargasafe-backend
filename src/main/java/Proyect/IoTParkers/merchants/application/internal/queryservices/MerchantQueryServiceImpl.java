package Proyect.IoTParkers.merchants.application.internal.queryservices;

import Proyect.IoTParkers.merchants.domain.exceptions.MerchantNotFoundException;
import Proyect.IoTParkers.merchants.domain.model.aggregates.Merchant;
import Proyect.IoTParkers.merchants.domain.model.queries.GetAllMerchantsQuery;
import Proyect.IoTParkers.merchants.domain.model.queries.GetMerchantByIdQuery;
import Proyect.IoTParkers.merchants.domain.services.MerchantQueryService;
import Proyect.IoTParkers.merchants.infrastructure.persistence.jpa.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantQueryServiceImpl implements MerchantQueryService {

    private final MerchantRepository merchantRepository;

    public MerchantQueryServiceImpl(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    @Override
    public List<Merchant> handle(GetAllMerchantsQuery query) {
        return this.merchantRepository.findAll();
    }

    @Override
    public Merchant handle(GetMerchantByIdQuery query) {
        var merchant = this.merchantRepository.findById(query.id());

        return merchant.orElseThrow(() -> new MerchantNotFoundException(query.id()));
    }
}
