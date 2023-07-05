package com.example.crud111.service;

import com.example.crud111.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService<T> {


  Optional<T> findById(Long id);
  List<T> existsByName(String carName);
  T save(T t);

  Car save(Car car);

  void remove(Long id);

  List<Car> findAll();

  void deleteById(Long id);

  void createCar(Car car);
}
