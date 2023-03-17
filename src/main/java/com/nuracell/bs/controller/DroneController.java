package com.nuracell.bs.controller;

import com.nuracell.bs.entity.Drone;
import com.nuracell.bs.service.DroneService;
import com.nuracell.bs.util.DroneErrorResponse;
import com.nuracell.bs.util.DroneNoteFoundException;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/drones")
public class DroneController {
    private final DroneService droneService;

    @GetMapping
    public List<Drone> getDronesLimit10(@PathParam("limit") Long limit,
                                        @PathParam("unlimited") boolean unlimited) {
        if (!unlimited) {
            return droneService.findDronesLimit(Objects.requireNonNullElse(limit, 5L));
        }
        return droneService.findAll();
    }

    @GetMapping("/all")
    public List<Drone> getAllRobots() {
        return droneService.findAll();
    }



    @GetMapping("{droneId}")
    public Drone getDroneById(@PathVariable("droneId") Long id) {
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
    public String deleteDrone(@PathVariable("droneId") Long id) {
        return droneService.deleteById(id);
    }

    @ExceptionHandler
    private ResponseEntity<DroneErrorResponse> handleException(Exception e) {
        DroneErrorResponse droneErrorResponse = new DroneErrorResponse(
                "Drone with this id not found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(droneErrorResponse, HttpStatus.NOT_FOUND);
    }
}
