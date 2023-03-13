package com.nuracell.bs.service;

import com.nuracell.bs.entity.Drone;
import com.nuracell.bs.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DroneService {

    private final DroneRepository droneRepository;

    public List<Drone> findAll() {
        return droneRepository.findAll();
    }

    public List<Drone> findDronesLimit(Long limit) {
//        return droneRepository.findAll(PageRequest.of(0, limit)).getContent();
        return droneRepository.findDronesLimit(limit);
    }


    public Drone findById(Long id) {
        return droneRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Drone with id %d not found".formatted(id)));
    }

    public Integer save(Drone drone) {
        Drone save = droneRepository.save(drone);
        if (save != null) {
            return 1;
        } else {
            return 0;
        }
    }

    public String update(Drone drone) {
        Optional<Drone> optionalDrone = getOptionalDrone(drone.getId());
        if (optionalDrone.isPresent()) {
            droneRepository.save(drone);
            return "Drone %d has been updated".formatted(drone.getId());
        } else {
            throw new IllegalStateException("Drone not found");
        }
    }

    public String deleteById(Long id) {
        if(getOptionalDrone(id).isPresent()) {
            droneRepository.deleteById(id);
            return "Drone %d has been deleted".formatted(id);
        } else {
            throw new IllegalStateException("[DELETE] Drone not found");
        }

    }

    public Optional<Drone> getOptionalDrone(Long id) {
        return droneRepository.findById(id);
    }
}
