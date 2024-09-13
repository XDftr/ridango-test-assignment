package com.ridango.assignment.service;

import com.ridango.assignment.entity.Player;
import com.ridango.assignment.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    /**
     * Retrieves a player by their username or creates a new player if none exists with the given username.
     *
     * @param username the username of the player to retrieve or create
     * @return the retrieved player or the newly created player if no player exists with the given username
     */
    public Player getPlayerByUsernameOrCreateNewPlayer(String username) {
        return playerRepository.getPlayerByUsername(username)
                .orElseGet(() -> {
                    Player newPlayer = new Player();
                    newPlayer.setUsername(username);
                    return playerRepository.save(newPlayer);
                });
    }

    public void savePlayer(Player player) {
        playerRepository.save(player);
    }
}
