package com.vinsguru.customer.service;

import com.vinsguru.customer.dto.CustomerDetails;
import com.vinsguru.customer.dto.GenreUpdateRequest;
import com.vinsguru.customer.exception.CustomerNotFoundException;
import com.vinsguru.customer.mapper.CustomerMapper;
import com.vinsguru.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CustomerService(CustomerRepository customerRepository, ApplicationEventPublisher eventPublisher) {
        this.customerRepository = customerRepository;
        this.eventPublisher = eventPublisher;
    }

    public CustomerDetails getCustomer(Integer customerId) {
        return this.customerRepository.findById(customerId)
                                      .map(CustomerMapper::toCustomerDetails)
                                      .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Transactional
    public void updateCustomerGenre(Integer customerId, GenreUpdateRequest request) {
        var customer = this.customerRepository.findById(customerId)
                                              .orElseThrow(() -> new CustomerNotFoundException(customerId));
        customer.setFavoriteGenre(request.favoriteGenre());
        this.eventPublisher.publishEvent(CustomerMapper.toGenreUpdatedEvent(customerId, request.favoriteGenre()));
    }

}
