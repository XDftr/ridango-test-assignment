package com.ridango.assignment.service;

import com.ridango.assignment.entity.Cocktail;
import com.ridango.assignment.entity.GameSession;
import com.ridango.assignment.entity.Player;
import com.ridango.assignment.exception.GameSessionException;
import com.ridango.assignment.repository.GameSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameSessionService {
    private final GameSessionRepository gameSessionRepository;
    private final CocktailService cocktailService;

    /**
     * Retrieves an ongoing game session for the given player or creates a new one if none exists.
     * If a new game session is created, it ensures that the player has not previously guessed the cocktail.
     *
     * @param player the player for whom to retrieve or create a game session
     * @return the ongoing or newly created game session for the specified player
     */
    public GameSession getOngoingGameSessionOrCreateNew(Player player) {
        Optional<GameSession> gameSession = gameSessionRepository.getGameSessionByPlayerAndIsCompleted(player, false);
        if (gameSession.isPresent()) {
            return gameSession.get();
        } else {
            List<Cocktail> guessedCocktails = gameSessionRepository.findGuessedCocktailsByPlayer(player);
            Cocktail newCocktail;

            do {
                newCocktail = cocktailService.getRandomCocktail();

            } while (guessedCocktails.contains(newCocktail));

            GameSession newGameSession = new GameSession();
            newGameSession.setPlayer(player);
            newGameSession.setCocktail(newCocktail);
            return gameSessionRepository.save(newGameSession);
        }
    }

    /**
     * Retrieves a game session by its unique ID.
     *
     * @param id the ID of the game session to retrieve
     * @return the GameSession with the specified ID
     * @throws GameSessionException if no game session with the specified ID is found
     */
    public GameSession getGameSessionById(Long id) {
        return gameSessionRepository.findById(id).orElseThrow(
                () -> new GameSessionException("No game session with ID: " + id + " found")
        );

    }

    /**
     * Persists the given game session to the repository.
     *
     * @param gameSession the game session to be saved
     */
    public void saveGameSession(GameSession gameSession) {
        gameSessionRepository.save(gameSession);
    }
}
