package com.terasoft.subscriptionbc.query.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionHistoryViewRepository extends JpaRepository<SubscriptionHistoryView, String> {

    @Query(value = "SELECT * FROM subscription_history_view WHERE subscription_history_id = (SELECT MAX(subscription_history_id) FROM subscription_history_view WHERE subscription_id = : customSubscriptionId)", nativeQuery = true)
    Optional<SubscriptionHistoryView> getLastByCustomSubscriptionId(String customSubscriptionId);

    @Query(value = "SELECT * FROM subscription_history_view WHERE subscription_id = :customSubscriptionId", nativeQuery = true)
    List<SubscriptionHistoryView> getHistoryBySubscriptionId(String customSubscriptionId);
}
