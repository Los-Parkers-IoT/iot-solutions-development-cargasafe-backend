package Proyect.IoTParkers.fleet.interfaces.rest;

import Proyect.IoTParkers.fleet.domain.exceptions.*;
import Proyect.IoTParkers.fleet.domain.model.commands.*;
import Proyect.IoTParkers.fleet.domain.model.queries.*;
import Proyect.IoTParkers.fleet.domain.services.DeviceCommandService;
import Proyect.IoTParkers.fleet.domain.services.DeviceQueryService;
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
@RequestMapping("/api/v1/fleet/devices")
@Tag(name = "Devices", description = "Endpoints for managing IoT devices in fleet management")
public class DeviceController {
    
    private final DeviceCommandService deviceCommandService;
    private final DeviceQueryService deviceQueryService;
    
    public DeviceController(DeviceCommandService deviceCommandService, DeviceQueryService deviceQueryService) {
        this.deviceCommandService = deviceCommandService;
        this.deviceQueryService = deviceQueryService;
    }
    
    @Operation(summary = "Get all devices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devices returned")
    })
    @GetMapping
    public ResponseEntity<List<DeviceResource>> getAllDevices() {
        var query = new GetAllDevicesQuery();
        var devices = this.deviceQueryService.handle(query);
        var resources = devices.stream()
                .map(DeviceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
    
    @Operation(summary = "Get device by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device returned"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResource> getDeviceById(@PathVariable Long id) {
        try {
            var query = new GetDeviceByIdQuery(id);
            var device = this.deviceQueryService.handle(query)
                    .orElseThrow(() -> new DeviceNotFoundException(id));
            var resource = DeviceResourceFromEntityAssembler.toResourceFromEntity(device);
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            if (e instanceof DeviceNotFoundException) {
                log.warn(e.getMessage());
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }
    
    @Operation(summary = "Get device by IMEI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device returned"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @GetMapping("/by-imei/{imei}")
    public ResponseEntity<DeviceResource> getDeviceByImei(@PathVariable String imei) {
        try {
            var query = new GetDeviceByImeiQuery(imei);
            var device = this.deviceQueryService.handle(query)
                    .orElseThrow(() -> new DeviceNotFoundException(imei));
            var resource = DeviceResourceFromEntityAssembler.toResourceFromEntity(device);
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            if (e instanceof DeviceNotFoundException) {
                log.warn(e.getMessage());
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }
    
    @Operation(summary = "Get devices by online status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Devices returned")
    })
    @GetMapping("/by-online/{online}")
    public ResponseEntity<List<DeviceResource>> getDevicesByOnline(@PathVariable boolean online) {
        var query = new GetDevicesByOnlineQuery(online);
        var devices = this.deviceQueryService.handle(query);
        var resources = devices.stream()
                .map(DeviceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Create device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "409", description = "Device with IMEI already exists")
    })
    @PostMapping
    public ResponseEntity<DeviceResource> createDevice(@Valid @RequestBody CreateDeviceResource resource) {
        try {
            var command = CreateDeviceCommandFromResourceAssembler.toCommandFromResource(resource);
            var device = this.deviceCommandService.handle(command)
                    .orElseThrow(() -> new RuntimeException("Failed to create device"));
            var deviceResource = DeviceResourceFromEntityAssembler.toResourceFromEntity(device);
            return new ResponseEntity<>(deviceResource, CREATED);
        } catch (RuntimeException e) {
            if (e instanceof DeviceImeiAlreadyExistsException || e instanceof VehicleAlreadyHasDeviceException) {
                log.warn(e.getMessage());
                return ResponseEntity.status(CONFLICT).body(null);
            }
            throw e;
        }
    }
    
    @Operation(summary = "Update device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device updated successfully"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResource> updateDevice(@PathVariable Long id,
                                                       @Valid @RequestBody UpdateDeviceResource resource) {
        try {
            var command = UpdateDeviceCommandFromResourceAssembler.toCommandFromResource(id, resource);
            var device = this.deviceCommandService.handle(command)
                    .orElseThrow(() -> new DeviceNotFoundException(id));
            var deviceResource = DeviceResourceFromEntityAssembler.toResourceFromEntity(device);
            return ResponseEntity.ok(deviceResource);
        } catch (RuntimeException e) {
            if (e instanceof DeviceNotFoundException) {
                log.warn(e.getMessage());
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }
    
    @Operation(summary = "Delete device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Device deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Device not found"),
            @ApiResponse(responseCode = "409", description = "Cannot delete device assigned to a vehicle")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        try {
            var command = new DeleteDeviceCommand(id);
            this.deviceCommandService.handle(command);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            if (e instanceof DeviceNotFoundException) {
                log.warn(e.getMessage());
                return ResponseEntity.notFound().build();
            }
            if (e instanceof DeviceAssignmentConflictException) {
                log.warn(e.getMessage());
                return ResponseEntity.status(CONFLICT).build();
            }
            throw e;
        }
    }
    
    @Operation(summary = "Update device firmware")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device firmware updated successfully"),
            @ApiResponse(responseCode = "404", description = "Device not found")
    })
    @PostMapping("/{id}/firmware")
    public ResponseEntity<DeviceResource> updateDeviceFirmware(@PathVariable Long id,
                                                               @RequestParam String firmware) {
        try {
            var command = new UpdateDeviceFirmwareCommand(id, firmware);
            var device = this.deviceCommandService.handle(command)
                    .orElseThrow(() -> new DeviceNotFoundException(id));
            var resource = DeviceResourceFromEntityAssembler.toResourceFromEntity(device);
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            if (e instanceof DeviceNotFoundException) {
                log.warn(e.getMessage());
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }


}
