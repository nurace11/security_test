package com.nuracell.bs.repository;

import com.nuracell.bs.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM Drone LIMIT ?"
    )
    List<Drone> findDronesLimit(Long limit);

    @Query(
            nativeQuery = true,
            value = "SELECT count(*) FROM Drone"
    )
    int dronesCount();
}
