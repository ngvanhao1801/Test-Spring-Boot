package com.example.crud111.Exception;

public class CarNameConflictException extends RuntimeException {
  public CarNameConflictException(String message) {
    super(message);
  }
}
