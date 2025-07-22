package com.anthony.parkinglot.controller;

import com.anthony.parkinglot.entity.Lot;
import com.anthony.parkinglot.service.ParkingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Parking Lot", description = "Parking lot management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class ParkingLotController {
    private final ParkingService parkingService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ParkingLotController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Operation(
            summary = "Create a parking lot",
            description = "Create a parking lot with specified number of slots (Admin only)"
    )
    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ObjectNode createLots(@RequestParam("noOfLots") int noOfLots) {
        String respMessage = parkingService.createParkingLot(noOfLots);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("message", respMessage);

        return jsonObject;
    }

    @Operation(
            summary = "Park a car",
            description = "Park a car in an available slot"
    )
    @GetMapping("/park")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ObjectNode park(@RequestParam("plateNo") String plateNo, @RequestParam("colour") String colour) {
        String respMessage = parkingService.parkCar(plateNo, colour);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("message", respMessage);

        return jsonObject;
    }

    @Operation(
            summary = "Remove a car",
            description = "Remove a car from parking and calculate charges"
    )
    @GetMapping("/remove")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ObjectNode remove(@RequestParam("plateNo") String plateNo, @RequestParam("hours") int hours) {
        String respMessage = parkingService.removeCar(plateNo, hours);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("message", respMessage);

        return jsonObject;
    }

    @Operation(
            summary = "Parking lot status",
            description = "Get the current status of all parking slots"
    )
    @GetMapping("/status")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ResponseBody
    public List<Lot> status() {
        return parkingService.status();
    }
}
