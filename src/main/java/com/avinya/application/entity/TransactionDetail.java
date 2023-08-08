package com.avinya.application.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
  name = "TRANSACTION_DETAIL")
public class TransactionDetail {

  @Id
  @GeneratedValue(
    strategy = GenerationType.IDENTITY)
  @Column(
    name = "TRANS_ID")
  private Long transactionId;

  @Column(
    name = "CUST_ID")
  private Long customerId;

  @Column(
    name = "TRANS_DATE")
  private Timestamp transactionDate;

  @Column(
    name = "TRANS_AMOUNT")
  private BigDecimal transactionAmount;

}
