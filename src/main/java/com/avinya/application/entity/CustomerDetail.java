package com.avinya.application.entity;

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

@Entity
@Table(
  name = "CUSTOMER_DETAIL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetail {

  @Id
  @GeneratedValue(
    strategy = GenerationType.IDENTITY)
  @Column(
    name = "CUST_ID")
  private Long customerId;

  @Column(
    name = "CUST_NAME")
  private String customerName;

}
