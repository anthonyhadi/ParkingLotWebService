package com.anthony.parkinglot.controller;

import com.anthony.parkinglot.entity.Lot;
import com.anthony.parkinglot.service.ParkingService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingLotControllerTest {

    @Mock
    private ParkingService parkingService;

    @InjectMocks
    private ParkingLotController parkingLotController;

    @BeforeEach
    void setUp() {
        reset(parkingService);
    }

    @Test
    void createLots_ShouldReturnMessage() {
        when(parkingService.createParkingLot(5)).thenReturn("Created parking lot with 5 slots");
        ObjectNode response = parkingLotController.createLots(5);
        assertEquals("Created parking lot with 5 slots", response.get("message").asText());
        verify(parkingService).createParkingLot(5);
    }

    @Test
    void park_ShouldReturnMessage() {
        when(parkingService.parkCar("ABC123", "Red")).thenReturn("Allocated slot number: 1");
        ObjectNode response = parkingLotController.park("ABC123", "Red");
        assertEquals("Allocated slot number: 1", response.get("message").asText());
        verify(parkingService).parkCar("ABC123", "Red");
    }

    @Test
    void remove_ShouldReturnMessage() {
        when(parkingService.removeCar("ABC123", 3)).thenReturn("Registration number ABC123 with Slot Number 1 is free with Charge 20");
        ObjectNode response = parkingLotController.remove("ABC123", 3);
        assertEquals("Registration number ABC123 with Slot Number 1 is free with Charge 20", response.get("message").asText());
        verify(parkingService).removeCar("ABC123", 3);
    }

    @Test
    void status_ShouldReturnListOfLots() {
        Lot lot1 = new Lot(1L, "ABC123", "Red");
        Lot lot2 = new Lot(2L, "XYZ789", "Blue");
        List<Lot> lots = Arrays.asList(lot1, lot2);
        when(parkingService.status()).thenReturn(lots);
        List<Lot> result = parkingLotController.status();
        assertEquals(2, result.size());
        assertEquals("ABC123", result.get(0).getRegNo());
        assertEquals("Blue", result.get(1).getColour());
        verify(parkingService).status();
    }

    @Test
    void status_ShouldReturnEmptyList() {
        when(parkingService.status()).thenReturn(Collections.emptyList());
        List<Lot> result = parkingLotController.status();
        assertTrue(result.isEmpty());
        verify(parkingService).status();
    }
}
