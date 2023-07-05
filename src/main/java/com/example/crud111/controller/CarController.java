package com.example.crud111.controller;

import com.example.crud111.model.Car;
import com.example.crud111.service.CarService;
import com.github.dockerjava.api.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Tên car không trùng nhau (Update,insert)
 * Xóa id không tồn tại thì báo lỗi (Không tìm thấy id đấy)
 * Select id không tồn tại thì báo lỗi (Không tìm thấy id đấy)
 * Select all mà không có thì báo lỗi không có bản ghi nào
 */

@RestController
@RequestMapping(path = "/control")
public class CarController {
  public static Logger logger = (Logger) LoggerFactory.getLogger(CarController.class);
  private CarService carService;
  public void CarController(CarService carService) {
    this.carService = carService;
  }

  @GetMapping("/cars/{id}")
  public ResponseEntity<Optional> getCarById(@PathVariable Long id) {
    try {
      Optional<Car> car = carService.findById();
      if (car.isEmpty()) {
        throw new NotFoundException("Không tìm thấy id đấy");
      }
      return ResponseEntity.ok(car);
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/cars")
  public ResponseEntity<List<Car>> getAllCars() {
    try {
      List<Car> car = carService.findAll();
      if (car.isEmpty()) {
        throw new NotFoundException("Không có bản ghi nào");
      }
      return ResponseEntity.ok(car);
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping("/cars/post")
  public ResponseEntity<String> createCar(@RequestBody Car car) {
    try {
      Car existingCar = (Car) carService.findById().orElse(null);
      if (existingCar != null && !existingCar.getId().equals(car.getId())) {
        throw new NotFoundException("Tên car không trùng nhau");
        }
      carService.createCar(car);
      return ResponseEntity.ok("Thành công");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @DeleteMapping("/cars/{id}")
  public ResponseEntity<String> deleteCarById(@PathVariable Long id) {
    try {
      Optional<Car> car = carService.findById();
      if (car.isEmpty()) {
        throw new NotFoundException("Không tìm thấy id đấy");
      }
      carService.deleteById(id);
      return ResponseEntity.ok("Thành công");
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }


//  @GetMapping("/cars")
//  public ResponseEntity<List<Car>> getAllCars() {
//    try {
//      List<Car> listCar = carRepository.findAll();
//      if (listCar.isEmpty()) {
//        throw new ChangeSetPersister.NotFoundException();
//      }
//      return ResponseEntity.ok(listCar);
//    } catch (ChangeSetPersister.NotFoundException e) {
//      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//    } catch (Exception e) {
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }
//  }
//
//  @GetMapping("/cars/{id}")
//  public ResponseEntity<Optional<Car>> getCarById(@PathVariable Long id) {
//    try {
//      Optional<Car> listCar = carRepository.findById(id);
//      if (listCar.isEmpty()) {
//        throw new NotFoundException("Không tìm thấy id đấy");
//      }
//      return ResponseEntity.ok(listCar);
//    } catch (NotFoundException e) {
//      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//    } catch (Exception e) {
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }
//  }
//
//  @PostMapping("/post")
//  public ResponseEntity<String> CreateCar(@RequestBody Car car) {
//    try {
//      Car existingCar = carRepository.findById(car.getId()).orElse(null);
//      if (existingCar != null && !existingCar.getId().equals(car.getId())) {
//        throw new carNameConflictException("Tên car không trùng nhau");
//      }
//      carRepository.save(car);
//      return ResponseEntity.ok("Thành công");
//    } catch (carNameConflictException e) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//    } catch (Exception e) {
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tên car không trùng nhau");
//    }
//  }
//  @PutMapping("/cars/{id}")
//  public ResponseEntity<Car> updateCategory(@PathVariable Long id,
//                                            @RequestBody Car car) {
//    Optional<Car> carOptional = carRepository.findById(id);
//    return carOptional.map(car1 -> {
//      car.setId(car1.getId());
//      return new ResponseEntity<>(carRepository.save(car), HttpStatus.OK);
//    }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//  }
//
//  @DeleteMapping("/cars/{id}")
//  public ResponseEntity<String> deleteCarById(@PathVariable Long id) {
//    try {
//      Optional<Car> listCar = carRepository.findById(id);
//      if (listCar == null) {
//        throw new NotFoundException("Không tìm thấy id đấy");
//      }
//      carRepository.deleteById(id);
//      return ResponseEntity.ok("Thành công");
//    } catch (NotFoundException e) {
//      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id đấy");
//    } catch (Exception e) {
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không tìm thấy id đấy");
//    }
//  }
}
