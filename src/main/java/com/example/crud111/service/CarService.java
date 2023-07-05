package com.example.crud111.service;

import com.example.crud111.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService<T> {

  Optional<T> findById();

  List<Car> findAll();

  void deleteById(Long id);

  void createCar(Car car);
}
