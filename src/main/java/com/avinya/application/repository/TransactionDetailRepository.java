package com.avinya.application.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.avinya.application.entity.TransactionDetail;

public interface TransactionDetailRepository extends CrudRepository<TransactionDetail, Long> {

  /**
   * This operation find all Transaction detail on the basis of customerId
   *
   * @param customerId
   * @return List<TransactionDetail>
   */
  public List<TransactionDetail> findAllByCustomerId(Long customerId);

  /**
   * This operation finds all transactions on the basis of customer id and given
   * time span
   *
   * @param customerId
   * @param fromTimestamp
   * @param toTimestamp
   * @return List<TransactionDetail>
   */
  public List<TransactionDetail> findAllByCustomerIdAndTransactionDateBetween(Long customerId, Timestamp fromTimestamp,
    Timestamp toTimestamp);
}
