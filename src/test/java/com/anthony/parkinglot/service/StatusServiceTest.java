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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatusServiceTest {

    @Mock
    private LotRepository lotRepository;

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @BeforeEach
    void setUp() {
        reset(lotRepository);
    }

    @Test
    void status_WhenLotsExist_ShouldReturnAllLots() {
        // Arrange
        List<Lot> lots = Arrays.asList(
                new Lot(1L, "ABC123", "Red"),
                new Lot(2L, "XYZ789", "Blue")
        );
        when(lotRepository.findAll()).thenReturn(lots);

        // Act
        List<Lot> result = parkingService.status();

        // Assert
        assertEquals(2, result.size());
        assertEquals("ABC123", result.get(0).getRegNo());
        assertEquals("Red", result.get(0).getColour());
        assertEquals("XYZ789", result.get(1).getRegNo());
        assertEquals("Blue", result.get(1).getColour());
        verify(lotRepository).findAll();
    }

    @Test
    void status_WhenNoLotsExist_ShouldReturnEmptyList() {
        // Arrange
        when(lotRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Lot> result = parkingService.status();

        // Assert
        assertTrue(result.isEmpty());
        verify(lotRepository).findAll();
    }

    @Test
    void status_WhenRepositoryThrowsException_ShouldPropagateException() {
        // Arrange
        when(lotRepository.findAll()).thenThrow(new RuntimeException("DB error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> parkingService.status());
        verify(lotRepository).findAll();
    }
}
