package Proyect.IoTParkers.merchants.domain.services;

import Proyect.IoTParkers.merchants.domain.model.aggregates.Merchant;
import Proyect.IoTParkers.merchants.domain.model.queries.GetAllMerchantsQuery;
import Proyect.IoTParkers.merchants.domain.model.queries.GetMerchantByIdQuery;

import java.util.List;
import java.util.Optional;

public interface MerchantQueryService {
    List<Merchant> handle(GetAllMerchantsQuery query);

    Optional<Merchant> handle(GetMerchantByIdQuery query);
}
