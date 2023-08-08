package com.avinya.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avinya.application.exception.CustomerRewardProgramException;
import com.avinya.application.model.CustomerRewardDetail;
import com.avinya.application.service.CustomerRewardService;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

/**
 * @apiNote This is main Controller class for Customer Reward Program
 *
 */
@RestController
@RequestMapping("/customer-reward-program/api/v1")
@Slf4j
public class CustomerRewardController {

  @Autowired
  private CustomerRewardService rewardsService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping(
    value = "/{customerId}/reward-points",
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CustomerRewardDetail> getRewardPointsByCustomerId(@PathVariable(
    required = false) final Long customerId) throws CustomerRewardProgramException {

    // Initial validation for customer
    if (customerId == null || customerId == 0L) {
      log.debug("Invalid Customer id {}", customerId);
      throw new ValidationException("Customer is not valid");
    }

    final CustomerRewardDetail customerRewardDetail = rewardsService.getRewardPointsByCustomerId(customerId);

    return new ResponseEntity<>(customerRewardDetail, HttpStatus.OK);
  }

}
