package com.terasoft.subscriptionbc.command.application.dtos.response;

import lombok.Value;

@Value
public class EditSubscriptionResponse {
    private String subscriptionId;
    private String amount;
    private String currency;
    private String description;
    private String lawyerId;
}
