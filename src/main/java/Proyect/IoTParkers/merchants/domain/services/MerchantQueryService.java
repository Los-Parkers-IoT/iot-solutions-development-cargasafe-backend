package Proyect.IoTParkers.merchants.domain.services;

import Proyect.IoTParkers.merchants.domain.model.aggregates.Merchant;
import Proyect.IoTParkers.merchants.domain.model.queries.GetAllMerchantsQuery;
import Proyect.IoTParkers.merchants.domain.model.queries.GetMerchantByIdQuery;

import java.util.List;

public interface MerchantQueryService {
    List<Merchant> handle(GetAllMerchantsQuery query);

    Merchant handle(GetMerchantByIdQuery query);
}
