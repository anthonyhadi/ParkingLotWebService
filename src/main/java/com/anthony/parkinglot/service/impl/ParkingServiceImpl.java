package com.anthony.parkinglot.service.impl;

import com.anthony.parkinglot.entity.Lot;
import com.anthony.parkinglot.repository.LotRepository;
import com.anthony.parkinglot.service.ParkingService;
import com.anthony.parkinglot.common.Message;
import com.anthony.parkinglot.common.util.ParkingLotUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new IllegalArgumentException(Message.INSERT_AT_LEAST_1);

        for (int i = 0; i < noOfLots; i++) {
            lotRepository.save(new Lot());
        }

        return String.format(Message.CREATED_WITH_SOME_SLOTS, noOfLots);
    }

    @Override
    public String parkCar(String regNo, String colour) {
        Lot lot = lotRepository.findFirstByPlateNoIsNullAndColourIsNull();
        if (lot == null)
            throw new RuntimeException(Message.PARKING_LOT_NOT_CREATED_OR_FULL);

        Long emptySlotId = lot.getId();
        try {
            lotRepository.save(new Lot(emptySlotId, regNo, colour));
        } catch (Exception e) {
            throw new IllegalArgumentException(Message.ERROR_DUPLICATE_PLATE_NO);
        }

        return String.format(Message.ALLOCATED_SLOT_NUMBER, emptySlotId);
    }

    @Override
    @Transactional
    public String removeCar(String regNo, int hours) {
        Lot lot = lotRepository.findFirstByPlateNo(regNo);
        if (lot == null)
            throw new RuntimeException(String.format(Message.PARKING_LOT_NOT_CREATED_OR_PLATE_NOT_EXISTS, regNo));

        lot.setPlateNo(null).setColour(null);

        int totalPay = ParkingLotUtil.calculatePrice(hours);
        return String.format(Message.SLOT_FREE_WITH_CHARGE, regNo, lot.getId(), totalPay);
    }

    @Override
    public List<Lot> status() {
        return lotRepository.findAll();
    }
} 