package com.avinya.application.service.helper;

import static com.avinya.application.util.CustomerRewardProgramConstant.DECIMAL_TWO;
import static com.avinya.application.util.CustomerRewardProgramConstant.FIRST_REWARD_LIMIT;
import static com.avinya.application.util.CustomerRewardProgramConstant.SECOND_REWARD_LIMIT;
import static java.math.BigDecimal.ZERO;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.avinya.application.entity.TransactionDetail;

/**
 * @implNote This class consists methods for Reward calculation
 *
 */
public class CustomerRewardServiceHelper {

  /**
   * get total reward points for monthly transactions
   *
   * @param transactions
   * @return Long
   */
  protected Long getRewardPointsPerMonth(final List<TransactionDetail> transactions) {
    return transactions.stream()
      .map(this::calculateRewardPoints)
      .collect(Collectors.summingLong(BigDecimal::longValue));
  }

  /**
   * Calculate Reward point as per transaction amount
   *
   * @param transactionDetail
   * @return BigDecimal
   */
  protected BigDecimal calculateRewardPoints(final TransactionDetail transactionDetail) {

    var rewardPoints = ZERO;

    final var transactionAmount = transactionDetail.getTransactionAmount();

    if (transactionAmount != null && transactionAmount.compareTo(ZERO) > 0) {

      final var overSecondRewardLimitTransaction = transactionAmount.subtract(SECOND_REWARD_LIMIT);

      if (overSecondRewardLimitTransaction.compareTo(ZERO) > INTEGER_ZERO) {
        /*
         * A customer receives 2 points for each dollar spent over $100 in every
         * transaction
         */
        rewardPoints = rewardPoints.add((overSecondRewardLimitTransaction.multiply(DECIMAL_TWO)));
      }

      if (transactionAmount.compareTo(FIRST_REWARD_LIMIT) > INTEGER_ZERO) {
        // Plus 1 reward point for each dollar spent over $50 in every transaction
        rewardPoints = rewardPoints.add(FIRST_REWARD_LIMIT);
      }
    }
    return rewardPoints;

  }

  /**
   * Method provides current Timestamp based of Off-Set Months
   *
   * @param months
   * @return Timestamp
   */
  protected Timestamp getDateBasedOnOffSetMonths(final int months) {
    return Timestamp.valueOf(LocalDateTime.now()
      .minusMonths(months));
  }
}
