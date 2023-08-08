package com.avinya.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avinya.application.entity.CustomerDetail;
import com.avinya.application.repository.CustomerDetailRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerDetailServiceImpl implements CustomerDetailService {

  @Autowired
  private CustomerDetailRepository customerRepository;

  @Override
  public CustomerDetail getCustomerIdById(final Long customerId) {
    log.info("Fetching Customer information");
    return customerRepository.findByCustomerId(customerId);
  }

}
