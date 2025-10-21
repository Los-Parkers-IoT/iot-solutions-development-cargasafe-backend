package Proyect.IoTParkers.merchants.interfaces.rest;


import Proyect.IoTParkers.merchants.domain.model.queries.GetAllMerchantsQuery;
import Proyect.IoTParkers.merchants.domain.services.MerchantCommandService;
import Proyect.IoTParkers.merchants.domain.services.MerchantQueryService;
import Proyect.IoTParkers.merchants.interfaces.rest.resources.MerchantResource;
import Proyect.IoTParkers.merchants.interfaces.rest.transformers.MerchantResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/merchants")
@Tag(name = "Merchants", description = "Endpoint for managing merchant sources")
public class MerchantController {

    private final MerchantCommandService merchantCommandService;
    private final MerchantQueryService merchantQueryService;

    public MerchantController(MerchantCommandService merchantCommandService, MerchantQueryService merchantQueryService) {
        this.merchantQueryService = merchantQueryService;
        this.merchantCommandService = merchantCommandService;
    }


    @Operation(summary = "Get All Merchant Sources")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Merchant source(s) returned")
    })
    @GetMapping
    public ResponseEntity<List<MerchantResource>> getAllMerchants() {
        var query = new GetAllMerchantsQuery();
        var merchants = this.merchantQueryService.handle(query);

        var resources = merchants.stream().map(MerchantResourceFromEntityAssembler::toResourceFromEntity).toList();

        return ResponseEntity.ok(resources);
    }


}
