package com.nuracell.bs.repository;

import com.nuracell.bs.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query(
            nativeQuery = true,
            value = "UPDATE player SET id = ?2 WHERE id = ?1"
    )
    @Modifying
    @Transactional
    Integer updateId(Long id, Long newId);
}
