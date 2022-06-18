package com.terasoft.subscriptionbc.command.domain.entities;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.terasoft.common.domain.enums.Currency;
import com.terasoft.subscriptionsbccontracts.commands.EditSubscription;
import com.terasoft.subscriptionsbccontracts.commands.RegisterSubscription;
import com.terasoft.subscriptionsbccontracts.events.SubscriptionEdited;
import com.terasoft.subscriptionsbccontracts.events.SubscriptionRegistered;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;

@Aggregate
public class Subscription {

    @AggregateIdentifier
    private String subscriptionId;

    private Float amount;

    private Currency currency;

    private String description;

    private String lawyerId;

    public Subscription() {}

    @CommandHandler
    public Subscription(RegisterSubscription command) {
        apply(
                new SubscriptionRegistered(
                        command.getSubscriptionId(),
                        command.getAmount(),
                        command.getCurrency(),
                        command.getDescription(),
                        command.getLawyerId(),
                        Instant.now()
                )
        );
    }

    @CommandHandler
    public void handle(EditSubscription command) {
        apply(
                new SubscriptionEdited(
                        command.getSubscriptionId(),
                        command.getAmount(),
                        command.getCurrency(),
                        command.getDescription(),
                        command.getLawyerId(),
                        Instant.now()
                )
        );
    }

    @EventSourcingHandler
    protected void on(SubscriptionRegistered event) {
        this.subscriptionId = event.getSubscriptionId();
        this.amount = event.getAmount();
        this.currency = event.getCurrency();
        this.description = event.getDescription();
        this.lawyerId = event.getLawyerId();
    }

    @EventSourcingHandler
    protected void on(SubscriptionEdited event) {
        this.amount = event.getAmount();
        this.currency = event.getCurrency();
        this.description = event.getDescription();
    }
}
