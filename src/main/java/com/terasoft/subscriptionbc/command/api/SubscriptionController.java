package com.terasoft.subscriptionbc.command.api;

import com.terasoft.common.api.ApiController;
import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import com.terasoft.subscriptionbc.command.application.dtos.request.EditSubscriptionRequest;
import com.terasoft.subscriptionbc.command.application.dtos.request.RegisterSubscriptionRequest;
import com.terasoft.subscriptionbc.command.application.dtos.response.EditSubscriptionResponse;
import com.terasoft.subscriptionbc.command.application.dtos.response.RegisterSubscriptionResponse;
import com.terasoft.subscriptionbc.command.application.services.SubscriptionApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/subscriptions")
@Tag(name = "Subscriptions")
public class SubscriptionController {
    private final SubscriptionApplicationService subscriptionApplicationService;
    private final CommandGateway commandGateway;

    public SubscriptionController(SubscriptionApplicationService subscriptionApplicationService, CommandGateway commandGateway) {
        this.subscriptionApplicationService = subscriptionApplicationService;
        this.commandGateway = commandGateway;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Post new Subscription")
    public ResponseEntity<Object> register(@RequestBody RegisterSubscriptionRequest registerSubscriptionRequest) {
        try {
            Result<RegisterSubscriptionResponse, Notification> result = subscriptionApplicationService.register(registerSubscriptionRequest);
            if (result.isSuccess()) {
                return ApiController.created(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch (Exception e) {
            return ApiController.serverError();
        }
    }

    @PutMapping("/{subscriptionId}")
    @Operation(summary = "Update Subscription by Id")
    public ResponseEntity<Object> edit(@PathVariable("subscriptionId") String subscriptionId, @RequestBody EditSubscriptionRequest editSubscriptionRequest) {
        try {
            editSubscriptionRequest.setSubscriptionId(subscriptionId);
            Result<EditSubscriptionResponse, Notification> result = subscriptionApplicationService.edit(editSubscriptionRequest);
            if (result.isSuccess()) {
                return ApiController.ok(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch (AggregateNotFoundException e) {
            return ApiController.notFound();
        } catch (Exception e) {
            return ApiController.serverError();
        }
    }

}
