package com.example.crud111.controller;

import com.example.crud111.Exception.CarIdNotFoundException;
import com.example.crud111.Exception.CarNameConflictException;
import com.example.crud111.Exception.NoFoundException;
import com.example.crud111.model.Car;
import com.example.crud111.repository.CarRepository;
import com.github.dockerjava.api.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
  private final CarRepository carRepository;
  @Autowired
  private ModelMapper modelMapper;

  public CarController(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  @GetMapping("/all")
  public ResponseEntity<String> getAllCars() {
    try {
      List<Car> listCar = carRepository.findAll();
      if (listCar.isEmpty()) {
        throw new ChangeSetPersister.NotFoundException();
      }
      return ResponseEntity.ok(listCar.toString());
    } catch (ChangeSetPersister.NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không có bản ghi nào");
    }
  }

  @GetMapping("/cars/{id}")
  public ResponseEntity<Optional<Car>> getCarById(@PathVariable Long id) {
    try {
      Optional<Car> listCar = carRepository.findById(id);
      if (listCar == null) {
        throw new NotFoundException("Không tìm thấy id đấy");
      }
      return ResponseEntity.ok(listCar);
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PostMapping("/post")
  public ResponseEntity<String> CreateCar(@RequestBody Car car) {
    try {
      Car existingCar = carRepository.findById(car.getId()).orElse(null);
      if (existingCar != null && !existingCar.getId().equals(car.getId())) {
        throw new CarNameConflictException("Tên car không trùng nhau");
      }
      carRepository.save(car);
      return ResponseEntity.ok("Thành công");
    } catch (CarNameConflictException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tên car không trùng nhau");
    }
  }

  @PutMapping("/cars/{id}")
  public ResponseEntity<Car> updateCategory(@PathVariable Long id,
                                            @RequestBody Car car) {
    Optional<Car> categoryOptional = carRepository.findById(id);
    return categoryOptional.map(category1 -> {
      car.setId(category1.getId());
      return new ResponseEntity<>(carRepository.save(car), HttpStatus.OK);
    }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/cars/{id}")
  public ResponseEntity<String> deleteCarById(@PathVariable Long id) {
    try {
      Optional<Car> listCar = carRepository.findById(id);
      if (listCar == null) {
        throw new NotFoundException("Không tìm thấy id đấy");
      }
      carRepository.deleteById(id);
      return ResponseEntity.ok("Thành công");
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy id đấy");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không tìm thấy id đấy");
    }
  }

  @RestControllerAdvice
  public class GlobalExceptionHandler {
    @ExceptionHandler(CarNameConflictException.class)
    public ResponseEntity<String> handleCarNameConflictException(CarNameConflictException ex) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CarIdNotFoundException.class)
    public ResponseEntity<String> handleCarIdNotFoundException(CarIdNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NoFoundException.class)
    public ResponseEntity<String> handleNoRecordFoundException(NoFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
  }
}
