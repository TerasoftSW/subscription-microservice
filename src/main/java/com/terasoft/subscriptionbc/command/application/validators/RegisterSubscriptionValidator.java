package com.terasoft.subscriptionbc.command.application.validators;

import com.terasoft.common.application.Notification;
import com.terasoft.subscriptionbc.command.application.dtos.request.RegisterSubscriptionRequest;
import org.springframework.stereotype.Component;

@Component
public class RegisterSubscriptionValidator {

    public RegisterSubscriptionValidator() {}

    public Notification validate(RegisterSubscriptionRequest registerSubscriptionRequest) {
        Notification notification = new Notification();
        String amount = registerSubscriptionRequest.getAmount().toString();
        if (amount.isEmpty()) {
            notification.addError("Subscription amount is required");
        }
        String currency = registerSubscriptionRequest.getCurrency();
        if (currency.isEmpty()) {
            notification.addError("Subscription currency is required");
        }
        String description = registerSubscriptionRequest.getDescription();
        if (description.isEmpty()) {
            notification.addError("Subscription description is required");
        }
        if (notification.hasErrors()) {
            return notification;
        }
        return notification;
    }
}
