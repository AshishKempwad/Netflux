package com.akempwad.customer.service;

import com.akempwad.customer.dto.CustomerDetails;
import com.akempwad.customer.dto.GenreUpdateRequest;
import com.akempwad.customer.exception.CustomerNotFoundException;
import com.akempwad.customer.mapper.CustomerMapper;
import com.akempwad.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CustomerService(CustomerRepository customerRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.customerRepository = customerRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public CustomerDetails getCustomer(Integer customerId) {

        return this.customerRepository.findById(customerId)
                .map(customer -> CustomerMapper.toCustomerDetails(customer))
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Transactional
    public void updateCustomerGenre(Integer customerId, GenreUpdateRequest request) {
        var customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        customer.setFavoriteGenre(request.favoriteGenre());
        this.applicationEventPublisher.publishEvent(CustomerMapper.toGenreUpdatedEvent(customerId, request.favoriteGenre()));

    }

}
