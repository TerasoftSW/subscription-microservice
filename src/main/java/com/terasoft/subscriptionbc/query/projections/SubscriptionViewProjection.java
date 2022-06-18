package com.terasoft.subscriptionbc.query.projections;

import com.terasoft.subscriptionsbccontracts.events.SubscriptionEdited;
import com.terasoft.subscriptionsbccontracts.events.SubscriptionRegistered;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class SubscriptionViewProjection {

    private final SubscriptionViewRepository subscriptionViewRepository;

    public SubscriptionViewProjection(SubscriptionViewRepository subscriptionViewRepository) {
        this.subscriptionViewRepository = subscriptionViewRepository;
    }

    @EventHandler
    public void on(SubscriptionRegistered event, @Timestamp Instant timestamp) {
        SubscriptionView subscriptionView = new SubscriptionView(event.getSubscriptionId(), event.getAmount(), event.getCurrency().toString(), event.getDescription(), event.getLawyerId(), event.getOccurredOn());
        subscriptionViewRepository.save(subscriptionView);
    }


    @EventHandler
    public void on(SubscriptionEdited event, @Timestamp Instant timestamp) {
        Optional<SubscriptionView> subscriptionViewOptional = subscriptionViewRepository.findById(event.getSubscriptionId().toString());
        if (subscriptionViewOptional.isPresent()) {
            SubscriptionView subscriptionView = subscriptionViewOptional.get();
            subscriptionView.setAmount(event.getAmount());
            subscriptionView.setCurrency(event.getCurrency().toString());
            subscriptionView.setDescription(event.getDescription());
            subscriptionViewRepository.save(subscriptionView);
        }
    }
}
