package com.nuracell.bs.controller;

import com.nuracell.bs.entity.Drone;
import com.nuracell.bs.service.DroneService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/drones")
public class DroneController {
    private final DroneService droneService;

    @GetMapping
    public List<Drone> getAllRobots() {
        return droneService.findAll();
    }

    @GetMapping("{droneId}")
    public Drone getDroneById(@PathParam("droneId") Long id) {
        return droneService.findById(id);
    }

    @PostMapping
    public Integer createDrone(@RequestBody Drone drone) {
        return  droneService.save(drone);
    }

    @PutMapping
    public String editDrone(@RequestBody Drone drone) {
        return droneService.update(drone);
    }

    @DeleteMapping("{droneId}")
    public String deleteDrone(@PathParam("droneId") Long id) {
        return droneService.deleteById(id);
    }
}
