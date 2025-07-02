package com.anthony.parkinglot.controller;

import com.anthony.parkinglot.entity.Lot;
import com.anthony.parkinglot.service.ParkingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    @GetMapping("/create")
    public ObjectNode createLots(@RequestParam("noOfLots") int noOfLots) {
        String respMessage = parkingService.createParkingLot(noOfLots);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("message", respMessage);

        return jsonObject;
    }

    @GetMapping("/park")
    public ObjectNode park(@RequestParam("plateNo") String plateNo, @RequestParam("colour") String colour) {
        String respMessage = parkingService.parkCar(plateNo, colour);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("message", respMessage);

        return jsonObject;
    }

    @GetMapping("/remove")
    public ObjectNode remove(@RequestParam("plateNo") String plateNo, @RequestParam("hours") int colour) {
        String respMessage = parkingService.removeCar(plateNo, colour);
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("message", respMessage);

        return jsonObject;
    }

    @GetMapping("/status")
    @ResponseBody
    public List<Lot> status() {
        return parkingService.status();
    }
}
