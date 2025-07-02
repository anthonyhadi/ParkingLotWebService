package com.anthony.parkinglot.service;

public interface ParkingService {
    
    /**
     * Creates a parking lot with the specified number of slots
     * @param noOfLots the number of parking slots to create
     * @return a message indicating the result of the operation
     */
    String createParkingLot(int noOfLots);

    String parkCar(String regNo, String colour);

    String removeCar(String regNo, int hours);
}
