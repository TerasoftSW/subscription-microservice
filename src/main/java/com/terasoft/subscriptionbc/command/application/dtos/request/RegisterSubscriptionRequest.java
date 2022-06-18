package com.terasoft.subscriptionbc.command.application.dtos.request;

import lombok.Value;

@Value
public class RegisterSubscriptionRequest {
    private Float amount;
    private String currency;
    private String description;
    private String lawyerId;
}
