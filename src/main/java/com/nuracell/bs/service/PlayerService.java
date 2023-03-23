package com.nuracell.bs.service;

import com.nuracell.bs.entity.Player;
import com.nuracell.bs.exception.PlayerNotFoundException;
import com.nuracell.bs.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }

    public Player updatePlayer(Long id, Player player) {
        Player updatePlayer = playerRepository.findById(id)
                        .orElseThrow(() -> new PlayerNotFoundException(id));

        updatePlayer.setName(player.getName());
        updatePlayer.setScore(player.getScore());
        return playerRepository.save(updatePlayer);
    }

    public void deletePlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));

        playerRepository.delete(player);
    }

    public Player updatePlayerName(Long id, String name) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
        player.setName(name);
        return playerRepository.save(player);
    }

    @Transactional
    public Player saveNewPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Integer updatePlayerId(Long id, Long newId) {
        playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
        return playerRepository.updateId(id, newId);
    }
}
