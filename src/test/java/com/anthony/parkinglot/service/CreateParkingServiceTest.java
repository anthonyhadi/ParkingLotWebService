package com.anthony.parkinglot.service;

import com.anthony.parkinglot.entity.Lot;
import com.anthony.parkinglot.repository.LotRepository;
import com.anthony.parkinglot.service.impl.ParkingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateParkingServiceTest {

    @Mock
    private LotRepository lotRepository;

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test
        reset(lotRepository);
    }

    @Test
    void createParkingLot_WithValidPositiveNumber_ShouldCreateParkingLot() {
        // Arrange
        int noOfLots = 5;
        when(lotRepository.save(any(Lot.class))).thenReturn(new Lot());

        // Act
        String result = parkingService.createParkingLot(noOfLots);

        // Assert
        assertEquals("Created parking lot with 5 slots", result);
        verify(lotRepository, times(5)).save(any(Lot.class));
    }

    @Test
    void createParkingLot_WithOneSlot_ShouldCreateParkingLot() {
        // Arrange
        int noOfLots = 1;
        when(lotRepository.save(any(Lot.class))).thenReturn(new Lot());

        // Act
        String result = parkingService.createParkingLot(noOfLots);

        // Assert
        assertEquals("Created parking lot with 1 slots", result);
        verify(lotRepository, times(1)).save(any(Lot.class));
    }

    @Test
    void createParkingLot_WithLargeNumber_ShouldCreateParkingLot() {
        // Arrange
        int noOfLots = 100;
        when(lotRepository.save(any(Lot.class))).thenReturn(new Lot());

        // Act
        String result = parkingService.createParkingLot(noOfLots);

        // Assert
        assertEquals("Created parking lot with 100 slots", result);
        verify(lotRepository, times(100)).save(any(Lot.class));
    }

    @Test
    void createParkingLot_WithZero_ShouldReturnErrorMessage() {
        // Arrange
        int noOfLots = 0;

        // Act
        String result = parkingService.createParkingLot(noOfLots);

        // Assert
        assertEquals("Please insert a number at least 1", result);
        verify(lotRepository, never()).save(any(Lot.class));
    }

    @Test
    void createParkingLot_WithNegativeNumber_ShouldReturnErrorMessage() {
        // Arrange
        int noOfLots = -5;

        // Act
        String result = parkingService.createParkingLot(noOfLots);

        // Assert
        assertEquals("Please insert a number at least 1", result);
        verify(lotRepository, never()).save(any(Lot.class));
    }

    @Test
    void createParkingLot_WithRepositoryException_ShouldPropagateException() {
        // Arrange
        int noOfLots = 3;
        when(lotRepository.save(any(Lot.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            parkingService.createParkingLot(noOfLots);
        });
        
        verify(lotRepository, times(1)).save(any(Lot.class));
    }
}
