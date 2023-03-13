package com.nuracell.bs.service;

import com.nuracell.bs.entity.Player;
import com.nuracell.bs.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findById(Long id) {
        return playerRepository.findById(id)
                .orElse(new Player(id, new BigInteger("0"), "unknown"));
//                .orElseThrow(() -> new IllegalStateException("Player with %d id not found".formatted(id)));
    }

    public Player updatePlayer(Long id, Player player) {
        Player updatePlayer = playerRepository.findById(id)
                        .orElseThrow(() -> new IllegalStateException("Player does not exist with id: %d".formatted(id)));

        updatePlayer.setId(player.getId());
        updatePlayer.setName(player.getName());
        updatePlayer.setScore(player.getScore());
        return playerRepository.save(updatePlayer);
    }

    public void deletePlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Player does not exist with id: %d".formatted(id)));

        playerRepository.delete(player);
    }

    public Player updatePlayerName(Long id, String name) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Player does not exist with id: %d".formatted(id)));
        player.setName(name);
        return playerRepository.save(player);
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }
}
