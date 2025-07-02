package com.anthony.parkinglot.service;

import com.anthony.parkinglot.entity.Lot;

import java.util.List;

public interface ParkingService {

    String createParkingLot(int noOfLots);

    String parkCar(String regNo, String colour);

    String removeCar(String regNo, int hours);

    List<Lot> status();
}
