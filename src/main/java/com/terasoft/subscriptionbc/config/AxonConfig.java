package com.terasoft.subscriptionbc.config;

import com.terasoft.subscriptionbc.command.domain.entities.Subscription;
import com.thoughtworks.xstream.XStream;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {
    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();

        xStream.allowTypesByWildcard(new String[] {
                "com.terasoft.subscriptionsbccontracts.**"
        });
        return xStream;
    }
    @Bean
    public Repository<Subscription> eventSourcingSubscriptionRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(Subscription.class)
                .eventStore(eventStore)
                .build();
    }
}
