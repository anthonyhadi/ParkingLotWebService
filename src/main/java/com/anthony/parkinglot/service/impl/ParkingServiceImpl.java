package com.anthony.parkinglot.service.impl;

import com.anthony.parkinglot.entity.Lot;
import com.anthony.parkinglot.repository.LotRepository;
import com.anthony.parkinglot.service.ParkingService;
import com.anthony.parkinglot.util.Message;
import com.anthony.parkinglot.util.ParkingLotUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final LotRepository lotRepository;

    public ParkingServiceImpl(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    @Override
    public String createParkingLot(int noOfLots) {
        if (noOfLots < 1)
            return Message.INSERT_AT_LEAST_1;

        for (int i = 0; i < noOfLots; i++) {
            lotRepository.save(new Lot());
        }

        return String.format(Message.CREATED_WITH_SOME_SLOTS, noOfLots);
    }

    @Override
    public String parkCar(String regNo, String colour) {
        Lot lot = lotRepository.findFirstByRegNoIsNullAndColourIsNull();
        if (lot == null) return Message.PARKING_LOT_NOT_CREATED_OR_FULL;

        Long emptySlotId = lot.getId();
        lotRepository.save(new Lot(emptySlotId, regNo, colour));

        return String.format(Message.ALLOCATED_SLOT_NUMBER, emptySlotId);
    }

    @Override
    public String removeCar(String regNo, int hours) {
        Lot lot = lotRepository.findFirstByRegNo(regNo);
        if (lot == null)
            return String.format(Message.PARKING_LOT_NOT_CREATED_OR_PLATE_NOT_EXISTS, regNo);

        lot.setRegNo(null);
        lot.setColour(null);

        int totalPay = ParkingLotUtil.calculatePrice(hours);
        return String.format(Message.SLOT_FREE_WITH_CHARGE, regNo, lot.getId(), totalPay);
    }

    @Override
    public List<Lot> status() {
        return lotRepository.findAll();
    }
} 