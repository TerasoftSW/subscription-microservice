package com.terasoft.subscriptionbc.query.projections;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class SubscriptionView {

    @Id
    @Column(length = 36)
    @Getter
    @Setter
    private String subscriptionId;

    @Column
    @Getter
    @Setter
    private Float amount;

    @Column
    @Getter
    @Setter
    private String currency;

    @Column(length = 50)
    @Getter
    @Setter
    private String description;

    @Column(length = 36)
    @Getter
    @Setter
    private String lawyerId;

    @Column(nullable = true)
    @Getter
    @Setter
    private Instant createdAt;

    public SubscriptionView() {}

    public SubscriptionView(String subscriptionId, Float amount, String currency, String description, String lawyerId, Instant createdAt) {
        this.subscriptionId = subscriptionId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.lawyerId = lawyerId;
        this.createdAt = createdAt;
    }
}
