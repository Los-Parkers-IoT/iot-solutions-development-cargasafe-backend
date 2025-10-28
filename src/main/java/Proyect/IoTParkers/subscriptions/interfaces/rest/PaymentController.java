package Proyect.IoTParkers.subscriptions.interfaces.rest;

import Proyect.IoTParkers.subscriptions.domain.model.queries.GetPaymentsByUserIdQuery;
import Proyect.IoTParkers.subscriptions.domain.services.PaymentCommandService;
import Proyect.IoTParkers.subscriptions.domain.services.PaymentQueryService;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.payment.CreatePaymentResource;
import Proyect.IoTParkers.subscriptions.interfaces.rest.resources.payment.PaymentResource;
import Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.payment.CreatePaymentCommandFromResourceAssembler;
import Proyect.IoTParkers.subscriptions.interfaces.rest.transformers.payment.PaymentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payment", description = "Payment Management Endpoints")
public class PaymentController {

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;

    public PaymentController(PaymentCommandService paymentCommandService, PaymentQueryService paymentQueryService) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService;
    }

    @PostMapping
    public ResponseEntity<PaymentResource> createPayment(@RequestBody CreatePaymentResource resource) {

        var createPaymentCommand = CreatePaymentCommandFromResourceAssembler
                .toCommandFromResource(resource);
        var payment = paymentCommandService.handle(createPaymentCommand);
        if (payment == null)
            return ResponseEntity.badRequest().build();

        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(payment);
        return new ResponseEntity<>(paymentResource, HttpStatus.CREATED);
    }

    @GetMapping("/user-id/{userId}")
    public ResponseEntity<List<PaymentResource>> getAllByUserId(@PathVariable Long userId) {

        var paymentList = paymentQueryService.handle(new GetPaymentsByUserIdQuery(userId));

        var paymentResourceList = paymentList.stream()
                .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(paymentResourceList);
    }

}
