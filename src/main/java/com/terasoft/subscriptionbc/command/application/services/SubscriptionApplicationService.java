package com.terasoft.subscriptionbc.command.application.services;

import com.terasoft.common.application.Notification;
import com.terasoft.common.application.Result;
import com.terasoft.common.application.ResultType;
import com.terasoft.common.domain.enums.Currency;
import com.terasoft.subscriptionbc.command.application.dtos.request.EditSubscriptionRequest;
import com.terasoft.subscriptionbc.command.application.dtos.request.RegisterSubscriptionRequest;
import com.terasoft.subscriptionbc.command.application.dtos.response.EditSubscriptionResponse;
import com.terasoft.subscriptionbc.command.application.dtos.response.RegisterSubscriptionResponse;
import com.terasoft.subscriptionbc.command.application.validators.EditSubscriptionValidator;
import com.terasoft.subscriptionbc.command.application.validators.RegisterSubscriptionValidator;
import com.terasoft.subscriptionsbccontracts.commands.EditSubscription;
import com.terasoft.subscriptionsbccontracts.commands.RegisterSubscription;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class SubscriptionApplicationService {
    private final RegisterSubscriptionValidator registerSubscriptionValidator;
    private final EditSubscriptionValidator editSubscriptionValidator;
    private final CommandGateway commandGateway;

    public SubscriptionApplicationService(RegisterSubscriptionValidator registerSubscriptionValidator, EditSubscriptionValidator editSubscriptionValidator, CommandGateway commandGateway) {
        this.registerSubscriptionValidator = registerSubscriptionValidator;
        this.editSubscriptionValidator = editSubscriptionValidator;
        this.commandGateway = commandGateway;
    }

    public Result<RegisterSubscriptionResponse, Notification> register(RegisterSubscriptionRequest registerSubscriptionRequest) throws Exception {
        Notification notification = this.registerSubscriptionValidator.validate(registerSubscriptionRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        String subscriptionId = UUID.randomUUID().toString();
        RegisterSubscription registerSubscription = new RegisterSubscription(
                subscriptionId,
                registerSubscriptionRequest.getAmount(),
                Currency.valueOf(registerSubscriptionRequest.getCurrency()),
                registerSubscriptionRequest.getDescription().trim(),
                registerSubscriptionRequest.getLawyerId()
        );
        CompletableFuture<Object> future = commandGateway.send(registerSubscription);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        RegisterSubscriptionResponse registerSubscriptionResponse = new RegisterSubscriptionResponse(
                registerSubscription.getSubscriptionId(),
                registerSubscription.getAmount().toString(),
                registerSubscription.getCurrency().toString(),
                registerSubscription.getDescription(),
                registerSubscription.getLawyerId()
        );
        return Result.success(registerSubscriptionResponse);
    }

    public Result<EditSubscriptionResponse, Notification> edit(EditSubscriptionRequest editSubscriptionRequest) throws Exception {
        Notification notification = this.editSubscriptionValidator.validate(editSubscriptionRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        EditSubscription editSubscription = new EditSubscription(
                editSubscriptionRequest.getSubscriptionId().trim(),
                editSubscriptionRequest.getAmount(),
                Currency.valueOf(editSubscriptionRequest.getCurrency()),
                editSubscriptionRequest.getDescription().trim(),
                editSubscriptionRequest.getLawyerId().trim()
        );
        CompletableFuture<Object> future = commandGateway.send(editSubscription);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        EditSubscriptionResponse editSubscriptionResponse = new EditSubscriptionResponse(
                editSubscription.getSubscriptionId(),
                editSubscription.getAmount().toString(),
                editSubscription.getCurrency().toString(),
                editSubscription.getDescription(),
                editSubscription.getLawyerId()
        );
        return Result.success(editSubscriptionResponse);
    }

}
