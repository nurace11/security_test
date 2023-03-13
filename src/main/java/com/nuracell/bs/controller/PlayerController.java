package com.nuracell.bs.controller;

import com.nuracell.bs.entity.Player;
import com.nuracell.bs.service.PlayerService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers() {
        System.out.println(this.getClass() + ": getPlayers");
        return new ResponseEntity<List<Player>>(playerService.findAll(), HttpStatusCode.valueOf(999));
    }

    @GetMapping("{playerId}")
    public ResponseEntity<Player> getPlayer(@PathVariable("playerId") Long id) {
        System.out.println(this.getClass() + ": getPlayer(%d)".formatted(id));
        return new ResponseEntity<Player>(playerService.findById(id), HttpStatusCode.valueOf(666));
    }

    @PostMapping
    public Player savePlayer(@RequestBody Player player) {
        return playerService.save(player);
    }

    @PutMapping("{playerId}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("playerId") Long id,
                                               @RequestBody Player player) {
        return new ResponseEntity<Player>(playerService.updatePlayer(id, player), HttpStatusCode.valueOf(777));
//        return ResponseEntity.ok(playerService.updatePlayer(id, player));
    }

    @DeleteMapping("playerId")
    public Map<String, Boolean> deletePlayer(@PathVariable("playerId") Long id) {
        playerService.deletePlayerById(id);
        return new HashMap<>(){{
            put("deleted:", Boolean.TRUE);
        }};
    }

    @PatchMapping("{id}/{name}")
    public ResponseEntity<Player> updatePlayerName(@PathVariable("playerId") Long id,
                                                    @PathVariable("name") String name) {
        try {
            return new ResponseEntity<Player>(playerService.updatePlayerName(id, name), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
