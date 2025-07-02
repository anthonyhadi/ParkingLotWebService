package com.anthony.parkinglot.service.impl;

import com.anthony.parkinglot.entity.Lot;
import com.anthony.parkinglot.repository.LotRepository;
import com.anthony.parkinglot.service.ParkingService;
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

        return "Created parking lot with " + noOfLots + " slots";
    }
} 