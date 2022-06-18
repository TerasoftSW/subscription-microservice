package com.terasoft.subscriptionbc.query.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionViewRepository extends JpaRepository<SubscriptionView, String> {
}
