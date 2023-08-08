package com.avinya.application.service;

import com.avinya.application.model.CustomerRewardDetail;

public interface CustomerRewardService {

  CustomerRewardDetail getRewardPointsByCustomerId(Long customerId);
}
