package com.example.crud111.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarDto {

  private String carName;

  private int year;

  private double price;

  private String address;

}
