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
class ParkCarServiceTest {

    @Mock
    private LotRepository lotRepository;

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @BeforeEach
    void setUp() {
        reset(lotRepository);
    }

    @Test
    void parkCar_WhenSlotAvailable_ShouldAllocateSlot() {
        // Arrange
        Lot emptyLot = new Lot(1L, null, null);
        when(lotRepository.findFirstByRegNoIsNullAndColourIsNull()).thenReturn(emptyLot);
        when(lotRepository.save(any(Lot.class))).thenReturn(new Lot(1L, "ABC123", "Red"));

        // Act
        String result = parkingService.parkCar("ABC123", "Red");

        // Assert
        assertEquals("Allocated slot number: 1", result);
        verify(lotRepository).findFirstByRegNoIsNullAndColourIsNull();
        verify(lotRepository).save(new Lot(1L, "ABC123", "Red"));
    }

    @Test
    void parkCar_WhenNoSlotAvailable_ShouldThrowException() {
        // Arrange
        when(lotRepository.findFirstByRegNoIsNullAndColourIsNull()).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            parkingService.parkCar("XYZ789", "Blue");
        });
        assertEquals(Message.PARKING_LOT_NOT_CREATED_OR_FULL, exception.getMessage());
        verify(lotRepository).findFirstByRegNoIsNullAndColourIsNull();
        verify(lotRepository, never()).save(any(Lot.class));
    }

    @Test
    void parkCar_WhenRepositoryThrowsException_ShouldPropagateException() {
        // Arrange
        Lot emptyLot = new Lot(2L, null, null);
        when(lotRepository.findFirstByRegNoIsNullAndColourIsNull()).thenReturn(emptyLot);
        when(lotRepository.save(any(Lot.class))).thenThrow(new RuntimeException("DB error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            parkingService.parkCar("LMN456", "Green");
        });
        verify(lotRepository).findFirstByRegNoIsNullAndColourIsNull();
        verify(lotRepository).save(new Lot(2L, "LMN456", "Green"));
    }
}
