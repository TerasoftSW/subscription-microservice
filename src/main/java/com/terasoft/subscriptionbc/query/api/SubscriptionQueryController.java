package com.terasoft.subscriptionbc.query.api;

import com.terasoft.subscriptionbc.query.projections.SubscriptionHistoryViewRepository;
import com.terasoft.subscriptionbc.query.projections.SubscriptionView;
import com.terasoft.subscriptionbc.query.projections.SubscriptionViewRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subscriptions")
@Tag(name = "Subscriptions")
public class SubscriptionQueryController {
    private final SubscriptionViewRepository subscriptionViewRepository;
    private final SubscriptionHistoryViewRepository subscriptionHistoryViewRepository;

    public SubscriptionQueryController(SubscriptionViewRepository subscriptionViewRepository, SubscriptionHistoryViewRepository subscriptionHistoryViewRepository) {
        this.subscriptionViewRepository = subscriptionViewRepository;
        this.subscriptionHistoryViewRepository = subscriptionHistoryViewRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<SubscriptionView>> getAll() {
        try {
            return new ResponseEntity<List<SubscriptionView>>(subscriptionViewRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionView> getById(@PathVariable("id") String id) {
        try {
            Optional<SubscriptionView> subscriptionViewOptional= subscriptionViewRepository.findById(id);
            if (subscriptionViewOptional.isPresent()) {
                return new ResponseEntity<SubscriptionView>(subscriptionViewOptional.get(), HttpStatus.OK);
            }
            return new ResponseEntity("NOT_FOUND", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
