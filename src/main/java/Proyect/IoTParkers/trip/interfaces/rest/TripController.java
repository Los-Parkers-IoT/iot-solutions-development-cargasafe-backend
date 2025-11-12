package Proyect.IoTParkers.trip.interfaces.rest;

import Proyect.IoTParkers.trip.domain.exceptions.OriginPointNotFoundException;
import Proyect.IoTParkers.trip.domain.exceptions.TripNotFoundException;
import Proyect.IoTParkers.trip.domain.model.commands.StartTripCommand;
import Proyect.IoTParkers.trip.domain.model.queries.GetAllTripsQuery;
import Proyect.IoTParkers.trip.domain.model.queries.GetTripByIdQuery;
import Proyect.IoTParkers.trip.domain.services.TripCommandService;
import Proyect.IoTParkers.trip.domain.services.TripQueryService;
import Proyect.IoTParkers.trip.interfaces.rest.resources.CreateTripResource;
import Proyect.IoTParkers.trip.interfaces.rest.resources.TripResource;
import Proyect.IoTParkers.trip.interfaces.rest.transformers.CreateTripCommandFromResourceAssembler;
import Proyect.IoTParkers.trip.interfaces.rest.transformers.TripResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
@Tag(name = "Trips", description = "Endpoint for managing trips sources")
public class TripController {

    @Autowired
    private final TripQueryService tripQueryService;
    @Autowired
    private final TripCommandService tripCommandService;

    private final HttpClient http = HttpClient.newHttpClient();
    @Value("${integrations.merchants.base-url:http://localhost:8080}")
    private String merchantsBaseUrl;

    @Operation(summary = "Get a trip by ID")
    @GetMapping("/{tripId}")
    public ResponseEntity<TripResource> getById(@PathVariable Long tripId) {
        var query = new GetTripByIdQuery(tripId);

        return tripQueryService.handle(query)
                .map(t -> ResponseEntity.ok(TripResourceFromEntityAssembler.toResourceFromEntity(t)))
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

        var list = page.map(TripResourceFromEntityAssembler::toResourceFromEntity).getContent();
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

        var list = page.map(TripResourceFromEntityAssembler::toResourceFromEntity).getContent();
        return ResponseEntity.ok(list);
    }


    @Operation(summary = "Create trip")
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody CreateTripResource resource) {
        try {
            var command = CreateTripCommandFromResourceAssembler.toCommandFromResource(resource);
            tripCommandService.handle(command);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            if (e instanceof OriginPointNotFoundException) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
            }
            throw e;
        }
    }


    @PostMapping("/{tripId}/start")
    public ResponseEntity startTrip(@PathVariable Long tripId) {
        try {
            tripCommandService.handle(new StartTripCommand(tripId));

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            if (e instanceof TripNotFoundException) {
                return ResponseEntity.notFound().build();
            }

            throw new RuntimeException(e);
        }
    }

//    @Operation(summary = "Update Trip status")
//    @PatchMapping("/{tripId}/status")
//    public ResponseEntity<TripResource> updateStatus(
//            @PathVariable UUID tripId,
//            @RequestBody UpdateTripStatusResource body) {
//
//
//        var updated = tripCommandService.updateStatus(
//                tripId,
//                updateStatusAssembler.status(body),
//                updateStatusAssembler.startedAt(body),
//                updateStatusAssembler.completedAt(body)
//        ).orElse(null);
//        return updated == null ? ResponseEntity.notFound().build()
//                : ResponseEntity.ok(assembler.toResource(updated));
//    }

    @Operation(summary = "List all trips")
    @GetMapping
    public List<TripResource> listAll() {
        var query = new GetAllTripsQuery();

        return tripQueryService.handle(query)
                .stream()
                .map(TripResourceFromEntityAssembler::toResourceFromEntity)
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
            if (res.statusCode() == 404)
                throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Merchant not found");
            if (res.statusCode() < 200 || res.statusCode() >= 300)
                throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY, "Merchants service error: HTTP " + res.statusCode());
        } catch (org.springframework.web.server.ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_GATEWAY, "Unable to reach Merchants service", e);
        }
    }

}

