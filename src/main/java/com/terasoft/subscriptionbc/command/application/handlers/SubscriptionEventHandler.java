package com.terasoft.subscriptionbc.command.application.handlers;

import com.terasoft.subscriptionsbccontracts.events.SubscriptionEdited;
import com.terasoft.subscriptionsbccontracts.events.SubscriptionRegistered;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;


@Component
public class SubscriptionEventHandler {
    public SubscriptionEventHandler() {}

    @EventHandler
    public void on(SubscriptionRegistered event) {}

    @EventHandler
    public void on(SubscriptionEdited event) {}
}
