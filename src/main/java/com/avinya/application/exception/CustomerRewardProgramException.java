package com.avinya.application.exception;

public class CustomerRewardProgramException extends Exception {

  private static final long serialVersionUID = 1L;

  // Parameterless Constructor
  public CustomerRewardProgramException() {
  }

  // Constructor that accepts a message
  public CustomerRewardProgramException(String message) {
    super(message);
  }
}
