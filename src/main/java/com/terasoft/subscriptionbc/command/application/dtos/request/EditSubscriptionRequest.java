package com.terasoft.subscriptionbc.command.application.dtos.request;

import lombok.Getter;
import lombok.Setter;

public class EditSubscriptionRequest {
    private @Getter @Setter String subscriptionId;
    private @Getter Float amount;
    private @Getter String currency;
    private @Getter String description;
    private @Getter String lawyerId;
}
