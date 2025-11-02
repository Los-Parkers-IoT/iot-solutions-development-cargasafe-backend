package Proyect.IoTParkers.fleet.interfaces.rest;

import Proyect.IoTParkers.fleet.domain.exceptions.*;
import Proyect.IoTParkers.fleet.domain.model.commands.*;
import Proyect.IoTParkers.fleet.domain.model.queries.*;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.VehicleStatus;
import Proyect.IoTParkers.fleet.domain.model.valueobjects.VehicleType;
import Proyect.IoTParkers.fleet.domain.services.VehicleCommandService;
import Proyect.IoTParkers.fleet.domain.services.VehicleQueryService;
import Proyect.IoTParkers.fleet.interfaces.rest.resources.*;
import Proyect.IoTParkers.fleet.interfaces.rest.transformers.*;
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
@RequestMapping("/api/v1/fleet/vehicles")
@Tag(name = "Vehicles", description = "Endpoints for managing vehicles in fleet management")
public class VehicleController {
    
    private final VehicleCommandService vehicleCommandService;
    private final VehicleQueryService vehicleQueryService;
    
    public VehicleController(VehicleCommandService vehicleCommandService, VehicleQueryService vehicleQueryService) {
        this.vehicleCommandService = vehicleCommandService;
        this.vehicleQueryService = vehicleQueryService;
    }
    
    @Operation(summary = "Get all vehicles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicles returned")
    })
    @GetMapping
    public ResponseEntity<List<VehicleResource>> getAllVehicles() {
        var query = new GetAllVehiclesQuery();
        var vehicles = this.vehicleQueryService.handle(query);
        var resources = vehicles.stream()
                .map(VehicleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
    
    @Operation(summary = "Get vehicle by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle returned"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResource> getVehicleById(@PathVariable Long id) {
        try {
            var query = new GetVehicleByIdQuery(id);
            var vehicle = this.vehicleQueryService.handle(query)
                    .orElseThrow(() -> new VehicleNotFoundException(id));
            var resource = VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle);
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            if (e instanceof VehicleNotFoundException) {
                log.warn(e.getMessage());
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }
    
    @Operation(summary = "Get vehicle by plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle returned"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @GetMapping("/by-plate/{plate}")
    public ResponseEntity<VehicleResource> getVehicleByPlate(@PathVariable String plate) {
        try {
            var query = new GetVehicleByPlateQuery(plate);
            var vehicle = this.vehicleQueryService.handle(query)
                    .orElseThrow(() -> new VehicleNotFoundException(plate));
            var resource = VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle);
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            if (e instanceof VehicleNotFoundException) {
                log.warn(e.getMessage());
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }
    
    @Operation(summary = "Get vehicles by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicles returned")
    })
    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<VehicleResource>> getVehiclesByStatus(@PathVariable String status) {
        var query = new GetVehiclesByStatusQuery(VehicleStatus.valueOf(status));
        var vehicles = this.vehicleQueryService.handle(query);
        var resources = vehicles.stream()
                .map(VehicleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
    
    @Operation(summary = "Get vehicles by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicles returned")
    })
    @GetMapping("/by-type/{type}")
    public ResponseEntity<List<VehicleResource>> getVehiclesByType(@PathVariable String type) {
        var query = new GetVehiclesByTypeQuery(VehicleType.valueOf(type));
        var vehicles = this.vehicleQueryService.handle(query);
        var resources = vehicles.stream()
                .map(VehicleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
    
    @Operation(summary = "Create vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "409", description = "Vehicle with plate already exists")
    })
    @PostMapping
    public ResponseEntity<VehicleResource> createVehicle(@Valid @RequestBody CreateVehicleResource resource) {
        try {
            var command = CreateVehicleCommandFromResourceAssembler.toCommandFromResource(resource);
            var vehicle = this.vehicleCommandService.handle(command)
                    .orElseThrow(() -> new RuntimeException("Failed to create vehicle"));
            var vehicleResource = VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle);
            return new ResponseEntity<>(vehicleResource, CREATED);
        } catch (RuntimeException e) {
            if (e instanceof VehiclePlateAlreadyExistsException || e instanceof DeviceAlreadyAssignedException) {
                log.warn(e.getMessage());
                return ResponseEntity.status(CONFLICT).body(null);
            }
            throw e;
        }
    }
    
    @Operation(summary = "Update vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle updated successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResource> updateVehicle(@PathVariable Long id, 
                                                         @Valid @RequestBody UpdateVehicleResource resource) {
        try {
            var command = UpdateVehicleCommandFromResourceAssembler.toCommandFromResource(id, resource);
            var vehicle = this.vehicleCommandService.handle(command)
                    .orElseThrow(() -> new VehicleNotFoundException(id));
            var vehicleResource = VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle);
            return ResponseEntity.ok(vehicleResource);
        } catch (RuntimeException e) {
            if (e instanceof VehicleNotFoundException) {
                log.warn(e.getMessage());
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }
    
    @Operation(summary = "Delete vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found"),
            @ApiResponse(responseCode = "409", description = "Cannot delete vehicle with assigned device or not RETIRED")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        try {
            var command = new DeleteVehicleCommand(id);
            this.vehicleCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e instanceof VehicleNotFoundException) {
                log.warn(e.getMessage());
                return ResponseEntity.notFound().build();
            }
            if (e instanceof DeviceAssignmentConflictException || e instanceof IllegalStateException) {
                log.warn(e.getMessage());
                return ResponseEntity.status(CONFLICT).build();
            }
            throw e;
        }
    }
    
    @Operation(summary = "Assign device to vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle or device not found"),
            @ApiResponse(responseCode = "409", description = "Vehicle already has device or device already assigned")
    })
    @PostMapping("/{id}/assign-device/{imei}")
    public ResponseEntity<VehicleResource> assignDeviceToVehicle(@PathVariable Long id, @PathVariable String imei) {
        try {
            var command = new AssignDeviceToVehicleCommand(id, imei);
            var vehicle = this.vehicleCommandService.handle(command)
                    .orElseThrow(() -> new VehicleNotFoundException(id));
            var resource = VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle);
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            if (e instanceof VehicleNotFoundException || e instanceof DeviceNotFoundException) {
                log.warn(e.getMessage());
                return ResponseEntity.notFound().build();
            }
            if (e instanceof VehicleAlreadyHasDeviceException || 
                e instanceof DeviceAlreadyAssignedException ||
                e instanceof IllegalStateException) {
                log.warn(e.getMessage());
                return ResponseEntity.status(CONFLICT).build();
            }
            throw e;
        }
    }
    
    @Operation(summary = "Unassign device from vehicle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device unassigned successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found"),
            @ApiResponse(responseCode = "409", description = "Vehicle has no device assigned")
    })
    @PostMapping("/{id}/unassign-device/{imei}")
    public ResponseEntity<VehicleResource> unassignDevice(@PathVariable Long id, @PathVariable String imei) {
        try {
            var command = new UnassignDeviceFromVehicleCommand(id, imei);
            var vehicle = this.vehicleCommandService.handle(command)
                    .orElseThrow(() -> new VehicleNotFoundException(id));
            var resource = VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle);
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
