package com.avinya.application.model;

import java.util.List;

import com.avinya.application.entity.TransactionDetail;

public record CustomerMonthlyTransaction(List<TransactionDetail> fistLastMonthTransactions,
    List<TransactionDetail> secondLastMonthTransactions, List<TransactionDetail> thirdLastMonthTransaction)

{
}
