package com.terasoft.subscriptionbc.command.application.validators;

import com.terasoft.common.application.Notification;
import com.terasoft.subscriptionbc.command.application.dtos.request.EditSubscriptionRequest;
import com.terasoft.subscriptionbc.command.application.dtos.request.RegisterSubscriptionRequest;
import com.terasoft.subscriptionbc.command.domain.entities.Subscription;
import org.axonframework.messaging.unitofwork.DefaultUnitOfWork;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.modelling.command.Repository;
import org.springframework.stereotype.Component;

@Component
public class EditSubscriptionValidator {

    private final Repository<Subscription> subscriptionRepository;

    public EditSubscriptionValidator(Repository<Subscription> subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Notification validate(EditSubscriptionRequest editSubscriptionRequest) {
        Notification notification = new Notification();
        String subscriptionId = editSubscriptionRequest.getSubscriptionId().trim();
        if (subscriptionId.isEmpty()) {
            notification.addError("Subscription id is required");
        }
        loadSubscriptionAggregate(subscriptionId);
        String amount = editSubscriptionRequest.getAmount().toString();
        if (amount.isEmpty()) {
            notification.addError("Subscription amount is required");
        }
        String currency = editSubscriptionRequest.getCurrency();
        if (currency.isEmpty()) {
            notification.addError("Subscription currency is required");
        }
        String description = editSubscriptionRequest.getDescription();
        if (description.isEmpty()) {
            notification.addError("Subscription description is required");
        }
        if (notification.hasErrors()) {
            return notification;
        }
        return notification;
    }

    private void loadSubscriptionAggregate(String subscriptionId) {
        UnitOfWork unitOfWork = null;
        try {
            unitOfWork = DefaultUnitOfWork.startAndGet(null);
            subscriptionRepository.load(subscriptionId);
            unitOfWork.commit();
        } catch (AggregateNotFoundException ex) {
            unitOfWork.commit();
            throw ex;
        } catch (Exception ex) {
            if (unitOfWork != null) {
                unitOfWork.rollback();
            }
        }
    }
}
