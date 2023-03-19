package com.nuracell.bs.controller;

import com.nuracell.bs.entity.Player;
import com.nuracell.bs.exception.PersonNotValidException;
import com.nuracell.bs.exception.PlayerNotFoundException;
import com.nuracell.bs.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/players")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "Players", description = "players API documentation")
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping
    @Operation(summary = "Get all players list")
    public ResponseEntity<List<Player>> getPlayers(
            @RequestParam(required = false) @Parameter(hidden = true) boolean flag) {

        return ResponseEntity.ok(playerService.findAll());
    }

    @GetMapping("{playerId}")
    @Operation(summary = "Information about a player by his id")
    public ResponseEntity<Player> getPlayer(
            @Parameter(description = "Player ID") @PathVariable("playerId") Long id) {

        return new ResponseEntity<>(playerService.findById(id), HttpStatusCode.valueOf(666));
    }

    @PostMapping
    @Operation(summary = "Register a new player")
    public ResponseEntity<Player> addPlayer(@RequestBody @Valid Player player,
                                            BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();

            if (errors.size() == 1 && errors.get(0).getField().equals("id")) {
                return ResponseEntity.ok(playerService.addPlayer(player));
            }

            StringBuilder errorMsg = new StringBuilder();

            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            throw new PersonNotValidException(errorMsg.toString());

        }

        return new ResponseEntity<>(playerService.addPlayer(player), HttpStatus.OK);
    }

    @PutMapping("{playerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')") // "hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')"
    @Operation(summary = "Update a player. Requires admin or moderator role")
    public ResponseEntity<Player> updatePlayer(@PathVariable("playerId") Long id,
                                               @RequestBody @Valid Player player,
                                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();

            if (errors.size() == 1 && errors.get(0).getField().equals("id")) {
                return ResponseEntity.ok(playerService.updatePlayer(id, player));
            }

            StringBuilder errorMsg = new StringBuilder();

            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            throw new PersonNotValidException(errorMsg.toString());

        }

        return ResponseEntity.ok(playerService.updatePlayer(id, player));
    }

    @DeleteMapping("{playerId}")
    @Secured("{ROLE_ADMIN, ROLE_MODERATOR}")
    @Operation(summary = "Delete player. Requires admin or moderator role")
    public Map<String, Boolean> deletePlayer(@PathVariable("playerId") Long id) {
        System.out.printf("[%s DELETE deletePlayer] id: %d %n", this.getClass(), id);

        playerService.deletePlayerById(id);
        return new HashMap<>(){{
            put("deleted:", Boolean.TRUE);
        }};
    }

    @PatchMapping("{playerId}/{name}")
    @RolesAllowed("{ROLE_ADMIN, ROLE_MODERATOR}") // JSR-250's @RolesAllowed of the @Secured annotation.
    @Operation(summary = "Update player's name. Requires admin or moderator role")
    public ResponseEntity<Player> updatePlayerName(@PathVariable("playerId") Long id,
                                                    @PathVariable("name") String name) {
        System.out.printf("[%s PATCH updatePlayerName] id: %d, name: %s %n", this.getClass(), id, name);

        try {
            return new ResponseEntity<Player>(playerService.updatePlayerName(id, name), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admins")
    @Operation(hidden = true)
    public ResponseEntity<String> getAdminPlayers() {
        return ResponseEntity.ok("Admin Players");
    }
}
