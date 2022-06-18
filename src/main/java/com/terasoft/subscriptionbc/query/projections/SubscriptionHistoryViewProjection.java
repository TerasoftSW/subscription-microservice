package com.terasoft.subscriptionbc.query.projections;

import com.terasoft.subscriptionsbccontracts.events.SubscriptionEdited;
import com.terasoft.subscriptionsbccontracts.events.SubscriptionRegistered;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class SubscriptionHistoryViewProjection {

    private final SubscriptionHistoryViewRepository subscriptionHistoryViewRepository;

    public SubscriptionHistoryViewProjection(SubscriptionHistoryViewRepository subscriptionHistoryViewRepository) {
        this.subscriptionHistoryViewRepository = subscriptionHistoryViewRepository;
    }

    @EventHandler
    public void on(SubscriptionRegistered event, @Timestamp Instant timestamp) {
        SubscriptionHistoryView subscriptionHistoryView = new SubscriptionHistoryView(event.getSubscriptionId(), event.getAmount(), event.getCurrency().toString(), event.getDescription(), event.getLawyerId(), event.getOccurredOn());
        subscriptionHistoryViewRepository.save(subscriptionHistoryView);
    }


    @EventHandler
    public void on(SubscriptionEdited event, @Timestamp Instant timestamp) {
        Optional<SubscriptionHistoryView> subscriptionHistoryViewOptional = subscriptionHistoryViewRepository.getLastByCustomSubscriptionId(event.getSubscriptionId().toString());
        if (subscriptionHistoryViewOptional.isPresent()) {
            SubscriptionHistoryView subscriptionHistoryView = subscriptionHistoryViewOptional.get();
            subscriptionHistoryView = new SubscriptionHistoryView(subscriptionHistoryView);
            subscriptionHistoryView.setAmount(event.getAmount());
            subscriptionHistoryView.setCurrency(event.getCurrency().toString());
            subscriptionHistoryView.setDescription(event.getDescription());
            subscriptionHistoryViewRepository.save(subscriptionHistoryView);
        }

    }
}
