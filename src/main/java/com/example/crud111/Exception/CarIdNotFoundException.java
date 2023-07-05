package com.example.crud111.Exception;

public class CarIdNotFoundException extends RuntimeException {
  public CarIdNotFoundException(String message) {
    super(message);
  }
}
