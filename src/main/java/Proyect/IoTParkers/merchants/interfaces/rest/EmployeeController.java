package Proyect.IoTParkers.merchants.interfaces.rest;

import Proyect.IoTParkers.merchants.domain.exceptions.EmployeeNotFoundException;
import Proyect.IoTParkers.merchants.domain.model.queries.GetAllEmployeesQuery;
import Proyect.IoTParkers.merchants.domain.model.queries.GetEmployeeByIdQuery;
import Proyect.IoTParkers.merchants.domain.model.queries.GetEmployeesByMerchantIdQuery;
import Proyect.IoTParkers.merchants.domain.services.EmployeeQueryService;
import Proyect.IoTParkers.merchants.interfaces.rest.resources.EmployeeResource;
import Proyect.IoTParkers.merchants.interfaces.rest.transformers.EmployeeResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employees", description = "Endpoint for managing employees sources")
public class EmployeeController {
    private final EmployeeQueryService employeeQueryService;

    public EmployeeController(EmployeeQueryService employeeQueryService) {
        this.employeeQueryService = employeeQueryService;
    }


    @Operation(summary = "Get All Employees Sources")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee source(s) returned")
    })
    @GetMapping
    public ResponseEntity<List<EmployeeResource>> getAllMEmployees() {
        var query = new GetAllEmployeesQuery();
        var entities = this.employeeQueryService.handle(query);
        var resources = entities.stream().map(EmployeeResourceFromEntityAssembler::toResourceFromEntity).toList();

        return ResponseEntity.ok(resources);
    }


    @Operation(summary = "Get Employees by Merchant ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee source(s) returned"),
    })
    @GetMapping("/merchants/{merchantId}")
    public ResponseEntity<List<EmployeeResource>> getEmployeesByMerchantId(@PathVariable Long merchantId) {
        var query = new GetEmployeesByMerchantIdQuery(merchantId);
        var entities = this.employeeQueryService.handle(query);
        var resources = entities.stream().map(EmployeeResourceFromEntityAssembler::toResourceFromEntity).toList();

        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Get Employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee source returned"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResource> getEmployeeById(@PathVariable Long id) {
        try {
            var query = new GetEmployeeByIdQuery(id);
            var entity = this.employeeQueryService.handle(query);
            var resource = EmployeeResourceFromEntityAssembler.toResourceFromEntity(entity);

            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            if (e instanceof EmployeeNotFoundException) {
                log.warn(e.getMessage());
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }

}
