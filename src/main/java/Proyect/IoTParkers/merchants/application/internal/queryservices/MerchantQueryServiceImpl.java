package Proyect.IoTParkers.merchants.application.internal.queryservices;

import Proyect.IoTParkers.merchants.domain.model.aggregates.Merchant;
import Proyect.IoTParkers.merchants.domain.model.queries.GetAllMerchantsQuery;
import Proyect.IoTParkers.merchants.domain.model.queries.GetMerchantByIdQuery;
import Proyect.IoTParkers.merchants.domain.services.MerchantQueryService;
import Proyect.IoTParkers.merchants.infrastructure.persistence.jpa.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Merchant> handle(GetMerchantByIdQuery query) {
        return this.merchantRepository.findById(query.id());
    }
}
