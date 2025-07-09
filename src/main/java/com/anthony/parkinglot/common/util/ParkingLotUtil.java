package com.anthony.parkinglot.common.util;

import com.anthony.parkinglot.common.Constant;

public class ParkingLotUtil {

    public static int calculatePrice(int hours) {
        return (hours - 1) * Constant.pricePerHour;
    }
}
