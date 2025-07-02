package com.anthony.parkinglot.util;

public class ParkingLotUtil {

    public static int calculatePrice(int hours) {
        return (hours - 1) * Constant.pricePerHour;
    }
}
