package com.nuracell.bs.controller;

import com.nuracell.bs.entity.Player;
import com.nuracell.bs.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Players", description = "players API documentation")
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getPlayers() {
        System.out.println(this.getClass() + ": getPlayers");

        return new ResponseEntity<List<Player>>(playerService.findAll(), HttpStatusCode.valueOf(999));
    }

    @GetMapping("{playerId}")
    @Operation(summary = "Information about a player by his id")
    public ResponseEntity<Player> getPlayer(
            @Parameter(description = "Player ID") @PathVariable("playerId") Long id) {
        System.out.println(this.getClass() + ": getPlayer(%d)".formatted(id));

        return new ResponseEntity<Player>(playerService.findById(id), HttpStatusCode.valueOf(666));
    }

    @PostMapping
    public /*ResponseEntity<*/Player savePlayer(@RequestBody Player player) {
        System.out.printf("[CONTROLLER] Player ID: %d%n", player.getId());

        return playerService.save(player);
//        return new ResponseEntity<Player>(playerService.save(player), HttpStatus.OK);
    }

    @PutMapping("{playerId}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("playerId") Long id,
                                               @RequestBody Player player) {
        System.out.printf("[%s PUT updatePlayer] id: %d, player: %s", this.getClass(), id, player);

        return new ResponseEntity<>(playerService.updatePlayer(id, player), HttpStatusCode.valueOf(777));
//        return ResponseEntity.ok(playerService.updatePlayer(id, player));
    }

    @DeleteMapping("{playerId}")
    public Map<String, Boolean> deletePlayer(@PathVariable("playerId") Long id) {
        System.out.printf("[%s DELETE deletePlayer] id: %d %n", this.getClass(), id);

        playerService.deletePlayerById(id);
        return new HashMap<>(){{
            put("deleted:", Boolean.TRUE);
        }};
    }

    @PatchMapping("{playerId}/{name}")
    public ResponseEntity<Player> updatePlayerName(@PathVariable("playerId") Long id,
                                                    @PathVariable("name") String name) {
        System.out.printf("[%s PATCH updatePlayerName] id: %d, name: %s %n", this.getClass(), id, name);

        try {
            return new ResponseEntity<Player>(playerService.updatePlayerName(id, name), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
