package com.terasoft.subscriptionbc.query.projections;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class SubscriptionHistoryView {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long subscriptionHistoryId;

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

    public SubscriptionHistoryView() {}

    public SubscriptionHistoryView(String subscriptionId, Float amount, String currency, String description, String lawyerId, Instant createdAt) {
        this.subscriptionId = subscriptionId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.lawyerId = lawyerId;
        this.createdAt = createdAt;
    }

    public SubscriptionHistoryView(SubscriptionHistoryView subscriptionHistoryView) {
        this.subscriptionId = subscriptionHistoryView.getSubscriptionId();
        this.amount = subscriptionHistoryView.getAmount();
        this.currency = subscriptionHistoryView.getCurrency();
        this.description = subscriptionHistoryView.getDescription();
    }


}
