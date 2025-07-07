package com.anthony.parkinglot.controller;

import com.anthony.parkinglot.entity.Lot;
import com.anthony.parkinglot.service.ParkingService;
import com.anthony.parkinglot.util.Message;
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
    void createLots_WhenInvalidInput_ShouldThrowException() {
        when(parkingService.createParkingLot(0)).thenThrow(new IllegalArgumentException(Message.INSERT_AT_LEAST_1));
        
        assertThrows(IllegalArgumentException.class, () -> {
            parkingLotController.createLots(0);
        });
        verify(parkingService).createParkingLot(0);
    }

    @Test
    void park_ShouldReturnMessage() {
        when(parkingService.parkCar("ABC123", "Red")).thenReturn("Allocated slot number: 1");
        ObjectNode response = parkingLotController.park("ABC123", "Red");
        assertEquals("Allocated slot number: 1", response.get("message").asText());
        verify(parkingService).parkCar("ABC123", "Red");
    }

    @Test
    void park_WhenNoSlotAvailable_ShouldThrowException() {
        when(parkingService.parkCar("XYZ789", "Blue")).thenThrow(new RuntimeException(Message.PARKING_LOT_NOT_CREATED_OR_FULL));
        
        assertThrows(RuntimeException.class, () -> {
            parkingLotController.park("XYZ789", "Blue");
        });
        verify(parkingService).parkCar("XYZ789", "Blue");
    }

    @Test
    void remove_ShouldReturnMessage() {
        when(parkingService.removeCar("ABC123", 3)).thenReturn("Registration number ABC123 with Slot Number 1 is free with Charge 20");
        ObjectNode response = parkingLotController.remove("ABC123", 3);
        assertEquals("Registration number ABC123 with Slot Number 1 is free with Charge 20", response.get("message").asText());
        verify(parkingService).removeCar("ABC123", 3);
    }

    @Test
    void remove_WhenCarNotFound_ShouldThrowException() {
        when(parkingService.removeCar("XYZ789", 2)).thenThrow(new RuntimeException(String.format(Message.PARKING_LOT_NOT_CREATED_OR_PLATE_NOT_EXISTS, "XYZ789")));
        
        assertThrows(RuntimeException.class, () -> {
            parkingLotController.remove("XYZ789", 2);
        });
        verify(parkingService).removeCar("XYZ789", 2);
    }

    @Test
    void status_ShouldReturnListOfLots() {
        Lot lot1 = new Lot(1L, "ABC123", "Red");
        Lot lot2 = new Lot(2L, "XYZ789", "Blue");
        List<Lot> lots = Arrays.asList(lot1, lot2);
        when(parkingService.status()).thenReturn(lots);
        List<Lot> result = parkingLotController.status();
        assertEquals(2, result.size());
        assertEquals("ABC123", result.get(0).getPlateNo());
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
