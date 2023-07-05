package com.example.crud111.service;

import com.example.crud111.model.Car;
import com.example.crud111.repository.CarRepository;

import java.util.List;
import java.util.Optional;

public class CarServiceImpl implements CarService {
  private CarRepository carRepository;
  public void CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  private CarService carService;
  public void CarController(CarService carService) {
    this.carService = carService;
  }

  @Override
  public Optional findById() {
   return carService.findById();
  }

  @Override
  public List<Car> findAll() {
    return carService.findAll();
  }

  @Override
  public void deleteById(Long id) {
    carService.deleteById(id);
  }

  @Override
  public void createCar(Car car) {
    carService.findById();
  }
}
