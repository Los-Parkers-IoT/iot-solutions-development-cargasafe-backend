package Proyect.IoTParkers.merchants.interfaces.rest.transformers;

import Proyect.IoTParkers.merchants.domain.model.aggregates.Merchant;
import Proyect.IoTParkers.merchants.interfaces.rest.resources.MerchantResource;

public final class MerchantResourceFromEntityAssembler {
    static public MerchantResource toResourceFromEntity(Merchant entity) {
        return new MerchantResource(
                entity.getId(),
                entity.getName(),
                entity.getContactEmail(),
                entity.getFiscalAddress(),
                entity.getRuc(),
                entity.isActive());
    }
}
