package com.anthony.parkinglot.service.impl;

import com.anthony.parkinglot.entity.Lot;
import com.anthony.parkinglot.repository.LotRepository;
import com.anthony.parkinglot.service.ParkingService;
import com.anthony.parkinglot.util.ParkingLotUtil;
import org.springframework.stereotype.Service;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final LotRepository lotRepository;

    public ParkingServiceImpl(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Override
    public String createParkingLot(int noOfLots) {
        if (noOfLots < 1)
            return "Please insert a number at least 1";

        for (int i = 0; i < noOfLots; i++) {
            lotRepository.save(new Lot());
        }

        return String.format("Created parking lot with %d slots", noOfLots);
    }

    @Override
    public String parkCar(String regNo, String colour) {
        Lot lot = lotRepository.findFirstByRegNoIsNullAndColourIsNull();
        if (lot == null) return "Either parking lot isn't created or full";

        Long emptySlotId = lot.getId();
        lotRepository.save(new Lot(emptySlotId, regNo, colour));

        return String.format("Allocated slot number: %d", emptySlotId);
    }

    @Override
    public String removeCar(String regNo, int hours) {
        Lot lot = lotRepository.findFirstByRegNo(regNo);
        if (lot == null)
            return String.format("Either parking lot isn't created or plate with no %s isn't found", regNo);

        lot.setRegNo(null);
        lot.setColour(null);

        return String.format("Registration number %s with Slot Number %d is free with Charge %s", regNo, lot.getId(), ParkingLotUtil.calculatePrice(hours));
    }
} 