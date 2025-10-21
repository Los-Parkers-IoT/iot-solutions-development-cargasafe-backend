package Proyect.IoTParkers.merchants.interfaces.rest;


import Proyect.IoTParkers.merchants.domain.exceptions.MerchantNotFoundException;
import Proyect.IoTParkers.merchants.domain.exceptions.MerchantRucAlreadyExistsException;
import Proyect.IoTParkers.merchants.domain.model.queries.GetAllMerchantsQuery;
import Proyect.IoTParkers.merchants.domain.model.queries.GetMerchantByIdQuery;
import Proyect.IoTParkers.merchants.domain.services.MerchantCommandService;
import Proyect.IoTParkers.merchants.domain.services.MerchantQueryService;
import Proyect.IoTParkers.merchants.interfaces.rest.resources.CreateMerchantResource;
import Proyect.IoTParkers.merchants.interfaces.rest.resources.MerchantResource;
import Proyect.IoTParkers.merchants.interfaces.rest.transformers.CreateMerchantCommandFromResourceAssembler;
import Proyect.IoTParkers.merchants.interfaces.rest.transformers.MerchantResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/v1/merchants")
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

    @Operation(summary = "Get Merchant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Merchant source returned"),
            @ApiResponse(responseCode = "404", description = "Merchant not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MerchantResource> getMerchantById(@PathVariable Long id) {
        try {
            var query = new GetMerchantByIdQuery(id);
            var entity = this.merchantQueryService.handle(query);
            var resource = MerchantResourceFromEntityAssembler.toResourceFromEntity(entity);

            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            if (e instanceof MerchantNotFoundException) {
                log.warn(e.getMessage());
                return ResponseEntity.notFound().build();
            }
            throw e;
        }

    }

    @Operation(summary = "Create Merchant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Merchant created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<MerchantResource> createMerchant(@Valid @RequestBody CreateMerchantResource resource) {
        try {
            var command = CreateMerchantCommandFromResourceAssembler.toCommandFromResource(resource);
            var entity = this.merchantCommandService.handle(command);
            var source = MerchantResourceFromEntityAssembler.toResourceFromEntity(entity);

            return new ResponseEntity<>(source, CREATED);
        } catch (RuntimeException e) {
            if (e instanceof MerchantRucAlreadyExistsException) {
                log.warn(e.getMessage());
                return ResponseEntity.status(CONFLICT).body(null);
            }
            throw e;
        }

    }


}
