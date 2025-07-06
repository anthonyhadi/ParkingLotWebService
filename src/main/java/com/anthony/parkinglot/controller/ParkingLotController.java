package com.anthony.parkinglot.controller;

import com.anthony.parkinglot.entity.Lot;
import com.anthony.parkinglot.service.ParkingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParkingLotController {
    private final ParkingService parkingService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ParkingLotController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Operation(
            summary = "Create a parking lot",
            description = "Detemine how many lots a parking lot has"
    )
    @GetMapping("/create")
    public ObjectNode createLots(@RequestParam("noOfLots") int noOfLots) {
        String respMessage = parkingService.createParkingLot(noOfLots);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("message", respMessage);

        return jsonObject;
    }

    @Operation(
            summary = "Park a car",
            description = "A car comes to park in the parking lot. The car plate number and colour will be noted"
    )
    @GetMapping("/park")
    public ObjectNode park(@RequestParam("plateNo") String plateNo, @RequestParam("colour") String colour) {
        String respMessage = parkingService.parkCar(plateNo, colour);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("message", respMessage);

        return jsonObject;
    }

    @Operation(
            summary = "Remove a car",
            description = "A car which already parked in the lot now goes out, leaves the occupied lot now empty"
    )
    @GetMapping("/remove")
    public ObjectNode remove(@RequestParam("plateNo") String plateNo, @RequestParam("hours") int colour) {
        String respMessage = parkingService.removeCar(plateNo, colour);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("message", respMessage);

        return jsonObject;
    }

    @Operation(
            summary = "Parking lot slots statuses",
            description = "Printing slots statuses of the parking lot"
    )
    @GetMapping("/status")
    @ResponseBody
    public List<Lot> status() {
        return parkingService.status();
    }
}
