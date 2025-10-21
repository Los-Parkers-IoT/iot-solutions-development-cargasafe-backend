package Proyect.IoTParkers.merchants.domain.model.queries;

import Proyect.IoTParkers.shared.domain.model.validation.Preconditions;

public record GetMerchantByIdQuery(Long id) {
    public GetMerchantByIdQuery {
        Preconditions.requireNonNull(id, "ID");
    }
}
