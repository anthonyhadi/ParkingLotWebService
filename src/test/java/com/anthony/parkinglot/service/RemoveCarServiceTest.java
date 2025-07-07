package com.anthony.parkinglot.service;

import com.anthony.parkinglot.entity.Lot;
import com.anthony.parkinglot.repository.LotRepository;
import com.anthony.parkinglot.service.impl.ParkingServiceImpl;
import com.anthony.parkinglot.util.Message;
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
class RemoveCarServiceTest {

    @Mock
    private LotRepository lotRepository;

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @BeforeEach
    void setUp() {
        reset(lotRepository);
    }

    @Test
    void removeCar_WhenCarExists_ShouldRemoveAndReturnMessage() {
        // Arrange
        Lot lot = new Lot(1L, "ABC123", "Red");
        when(lotRepository.findFirstByRegNo("ABC123")).thenReturn(lot);

        // Act
        String result = parkingService.removeCar("ABC123", 3);

        // Assert
        assertEquals("Registration number ABC123 with Slot Number 1 is free with Charge 20", result);
        verify(lotRepository).findFirstByRegNo("ABC123");
        // No save call since the method only sets fields to null, doesn't persist
    }

    @Test
    void removeCar_WhenCarNotFound_ShouldThrowException() {
        // Arrange
        when(lotRepository.findFirstByRegNo("XYZ789")).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            parkingService.removeCar("XYZ789", 2);
        });
        assertEquals(String.format(Message.PARKING_LOT_NOT_CREATED_OR_PLATE_NOT_EXISTS, "XYZ789"), exception.getMessage());
        verify(lotRepository).findFirstByRegNo("XYZ789");
        verify(lotRepository, never()).save(any(Lot.class));
    }

    @Test
    void removeCar_WhenRepositoryThrowsException_ShouldPropagateException() {
        // Arrange
        when(lotRepository.findFirstByRegNo("ERR123")).thenThrow(new RuntimeException("DB error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            parkingService.removeCar("ERR123", 5);
        });
        verify(lotRepository).findFirstByRegNo("ERR123");
    }

    @Test
    void removeCar_WithDifferentHours_ShouldCalculateCorrectCharge() {
        // Arrange
        Lot lot = new Lot(2L, "TEST123", "Blue");
        when(lotRepository.findFirstByRegNo("TEST123")).thenReturn(lot);

        // Act
        String result = parkingService.removeCar("TEST123", 1);

        // Assert
        assertEquals("Registration number TEST123 with Slot Number 2 is free with Charge 0", result);
        verify(lotRepository).findFirstByRegNo("TEST123");
    }
}
