package Proyect.IoTParkers.trip.interfaces.rest;

import Proyect.IoTParkers.trip.domain.model.queries.GetAllOriginPointsQuery;
import Proyect.IoTParkers.trip.domain.services.OriginPointCommandService;
import Proyect.IoTParkers.trip.domain.services.OriginPointQueryService;
import Proyect.IoTParkers.trip.interfaces.rest.resources.CreateOriginPointResource;
import Proyect.IoTParkers.trip.interfaces.rest.resources.OriginPointResource;
import Proyect.IoTParkers.trip.interfaces.rest.transformers.CreateOriginPointCommandFromResourceAssembler;
import Proyect.IoTParkers.trip.interfaces.rest.transformers.OriginPointResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/origin-points")
@Tag(name = "Origin Points", description = "Endpoint for managing origin points sources")
public class OriginPointController {

    @Autowired
    private OriginPointQueryService originPointQueryService;
    @Autowired
    private OriginPointCommandService originPointCommandService;


    @GetMapping
    public ResponseEntity<List<OriginPointResource>> getAllOriginPoints() {
        var query = new GetAllOriginPointsQuery();

        var originPoints = originPointQueryService.handle(query);

        var resources = originPoints.stream().map(OriginPointResourceFromEntityAssembler::toResourceFromEntity).toList();

        return ResponseEntity.ok(resources);
    }


    @PostMapping
    public ResponseEntity<OriginPointResource> createOriginPoint(@RequestBody CreateOriginPointResource resource) {
        var command = CreateOriginPointCommandFromResourceAssembler.toCommandFromResource(resource);

        var originPoint = originPointCommandService.handle(command);

        var response = OriginPointResourceFromEntityAssembler.toResourceFromEntity(originPoint);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
