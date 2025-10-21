package Proyect.IoTParkers.merchants.domain.exceptions;

public class MerchantRucAlreadyExistsException extends RuntimeException {
    public MerchantRucAlreadyExistsException(String ruc) {
        super("Error while creating merchant, RUC %s already exists".formatted(ruc));
    }
}
