package Proyect.IoTParkers.trip.interfaces.rest;

import Proyect.IoTParkers.trip.domain.model.valueobjects.TripStatus;
import Proyect.IoTParkers.trip.domain.services.TripCommandService;
import Proyect.IoTParkers.trip.domain.services.TripQueryService;
import Proyect.IoTParkers.trip.interfaces.rest.resources.CreateTripResource;
import Proyect.IoTParkers.trip.interfaces.rest.resources.TripResource;
import Proyect.IoTParkers.trip.interfaces.rest.resources.UpdateTripStatusResource;
import Proyect.IoTParkers.trip.interfaces.rest.transformers.TripResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
@Validated
public class TripController {

    private final TripQueryService tripQueryService;
    private final TripResourceFromEntityAssembler assembler;
    private final TripCommandService tripCommandService;
    private final Proyect.IoTParkers.trip.interfaces.rest.transformers.CreateTripCommandFromResourceAssembler createTripAssembler;
    private final Proyect.IoTParkers.trip.interfaces.rest.transformers.UpdateTripStatusCommandFromResourceAssembler updateStatusAssembler;

    @Value("${integrations.merchants.base-url:http://localhost:8080}")
    private String merchantsBaseUrl;

    private final HttpClient http = HttpClient.newHttpClient();

    @Operation(summary="Get a trip by ID")
    @GetMapping("/{tripId}")
    public ResponseEntity<TripResource> getById(@PathVariable UUID tripId){
        return tripQueryService.getById(tripId)
                .map(t -> ResponseEntity.ok(assembler.toResource(t)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "List trips by business (simple)")
    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<java.util.List<TripResource>> getByMerchantSimple(
            @PathVariable Long merchantId) {

        assertMerchantExists(merchantId);

        var page = tripQueryService.getByMerchant(
                merchantId,
                null,
                null,
                null,
                org.springframework.data.domain.Pageable.unpaged()
        );

        var list = page.map(assembler::toResource).getContent();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Search trips by date range (simple)")
    @GetMapping("/search")
    public ResponseEntity<java.util.List<TripResource>> searchByDatesSimple(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to) {

        if (from.isAfter(to)) {
            throw new IllegalArgumentException("from cannot be greater than to");
        }

        var page = tripQueryService.searchByDateRange(
                from,
                to,
                null,
                null,
                org.springframework.data.domain.Pageable.unpaged()
        );

        var list = page.map(assembler::toResource).getContent();
        return ResponseEntity.ok(list);
    }


    @Operation(summary = "Create trip ")
    @PostMapping
    public ResponseEntity<TripResource> create(@RequestBody CreateTripResource body){
        var created = tripCommandService.create(
                createTripAssembler.merchantId(body),
                createTripAssembler.createdAt(body));
        return ResponseEntity.ok(assembler.toResource(created));
    }


    @Operation(summary = "Update Trip status")
    @PatchMapping("/{tripId}/status")
    public ResponseEntity<TripResource> updateStatus(
            @PathVariable UUID tripId,
            @RequestBody UpdateTripStatusResource body) {
        var updated = tripCommandService.updateStatus(
                tripId,
                updateStatusAssembler.status(body),
                updateStatusAssembler.startedAt(body),
                updateStatusAssembler.completedAt(body)
        ).orElse(null);
        return updated == null ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(assembler.toResource(updated));
    }

    @Operation(summary = "List all trips")
    @GetMapping
    public List<TripResource> listAll() {
        return tripQueryService.getAll()
                .stream()
                .map(assembler::toResource)
                .toList();
    }



    private void assertMerchantExists(Long merchantId) {
        try {
            String bearer = null;
            var attrs = org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
            if (attrs instanceof org.springframework.web.context.request.ServletRequestAttributes sra) {
                bearer = sra.getRequest().getHeader("Authorization");
            }
            var reqBuilder = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(merchantsBaseUrl + "/api/v1/merchants/" + merchantId))
                    .GET();
            if (bearer != null && !bearer.isBlank()) reqBuilder.header("Authorization", bearer);

            var res = http.send(reqBuilder.build(), java.net.http.HttpResponse.BodyHandlers.discarding());
            if (res.statusCode() == 404) throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Merchant not found");
            if (res.statusCode() < 200 || res.statusCode() >= 300)
                throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY, "Merchants service error: HTTP " + res.statusCode());
        } catch (org.springframework.web.server.ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY, "Unable to reach Merchants service", e);
        }
    }

}

