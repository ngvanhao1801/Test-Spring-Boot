package com.example.crud111.service;

import com.example.crud111.model.Car;
import com.example.crud111.repository.CarRepository;

import java.util.List;
import java.util.Optional;

public class CarServiceImpl implements CarService {
  private CarRepository categoryRepository;

  public CarServiceImpl(CarRepository carRepository) {
    
    this.categoryRepository = carRepository;
  }

  @Override
  public Optional<Car> findById(Long id) {

    return categoryRepository.findById(id);
  }

  @Override
  public List existsByName(String carName) {
    return null;
  }

  @Override
  public Object save(Object o) {
    return null;
  }

  @Override
  public Car save(Car car) {
    return categoryRepository.save(car);
  }

  @Override
  public void remove(Long id) {
    categoryRepository.deleteById(id);
  }

  @Override
  public List<Car> findAll() {
    return null;
  }

  @Override
  public void deleteById(Long id) {

  }

  @Override
  public void createCar(Car car) {

  }
}
