package com.ridango.assignment.service;

import com.ridango.assignment.dto.GameSessionResponseDto;
import com.ridango.assignment.entity.GameSession;
import com.ridango.assignment.entity.Player;
import com.ridango.assignment.entity.RevealedInfo;
import com.ridango.assignment.entity.RevealedLetters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameService {
    private final PlayerService playerService;
    private final GameSessionService gameSessionService;
    private final RevealedLettersService revealedLettersService;
    private final RevealedInfoService revealedInfoService;
    private final Random random = new Random();

    /**
     * Creates a new game session or retrieves an ongoing one for the given username.
     * If the player does not exist, a new player is created.
     *
     * @param username the username of the player for whom to create or retrieve a game session
     * @return a GameSessionResponseDto containing information about the game session
     */
    public GameSessionResponseDto createGame(String username) {
        Player player = playerService.getPlayerByUsernameOrCreateNewPlayer(username);
        GameSession gameSession = gameSessionService.getOngoingGameSessionOrCreateNew(player);
        return createResponse(gameSession);
    }

    /**
     * Processes a guessed cocktail name for a given game session.
     * Checks whether the guessed name matches the cocktail name in the current game session,
     * and updates the game session based on the accuracy of the guess.
     *
     * @param cocktailName the guessed name of the cocktail
     * @param gameSessionId the ID of the game session
     * @return a GameSessionResponseDto containing information about the updated game session
     */
    public GameSessionResponseDto guessCocktail(String cocktailName, Long gameSessionId) {
        GameSession gameSession = getActiveGameSession(gameSessionId);

        if (cocktailName.equals(gameSession.getCocktail().getName())) {
            return handleCorrectGuess(gameSession);
        }

        return handleIncorrectGuess(gameSession);
    }

    /**
     * Retrieves the active game session for the given game session ID.
     * If the game session is completed, a new ongoing session is created or retrieved.
     *
     * @param gameSessionId the ID of the game session to retrieve
     * @return the active game session, either ongoing or newly created if the original session was completed
     */
    private GameSession getActiveGameSession(Long gameSessionId) {
        GameSession gameSession = gameSessionService.getGameSessionById(gameSessionId);
        if (gameSession.isCompleted()) {
            gameSession = gameSessionService.getOngoingGameSessionOrCreateNew(gameSession.getPlayer());
        }
        return gameSession;
    }

    /**
     * Handles the scenario where the player has made a correct guess in the current game session.
     * Updates the player's score based on the remaining attempts and marks the game session as completed.
     * Creates a new game session or retrieves the ongoing session for the player.
     *
     * @param gameSession the current game session of the player
     * @return a GameSessionResponseDto containing information about the updated game session
     */
    private GameSessionResponseDto handleCorrectGuess(GameSession gameSession) {
        Player player = gameSession.getPlayer();
        player.setScore(player.getScore() + gameSession.getAttemptsLeft());
        playerService.savePlayer(player);

        gameSession.setCompleted(true);
        gameSessionService.saveGameSession(gameSession);

        GameSession newGameSession = gameSessionService.getOngoingGameSessionOrCreateNew(player);

        return createResponse(newGameSession);
    }

    /**
     * Handles an incorrect guess by the player in the current game session.
     * Decreases the number of attempts left and updates the game session state accordingly.
     * If attempts are still remaining, reveals additional letters and information.
     * If no attempts are left, completes the current game session and starts a new one.
     *
     * @param gameSession the current game session of the player
     * @return a GameSessionResponseDto containing information about the updated game session
     */
    private GameSessionResponseDto handleIncorrectGuess(GameSession gameSession) {
        Short attemptsLeft = gameSession.getAttemptsLeft();
        attemptsLeft--;

        if (attemptsLeft > 0) {
            gameSession.setAttemptsLeft(attemptsLeft);
            gameSessionService.saveGameSession(gameSession);

            revealAdditionalLetters(gameSession);
            revealAdditionalInfo(gameSession, attemptsLeft);

            return createResponse(gameSession);
        }

        completeGameSession(gameSession);
        GameSession newGameSession = gameSessionService.getOngoingGameSessionOrCreateNew(gameSession.getPlayer());
        return createResponse(newGameSession);
    }

    /**
     * Reveals additional letters in the cocktail name for a given game session.
     * It randomly selects letters from the cocktail name that haven't been revealed yet
     * and reveals 10% of the total letters, provided there are more than 2 unrevealed positions.
     *
     * @param gameSession the game session for which to reveal additional letters
     */
    private void revealAdditionalLetters(GameSession gameSession) {
        String correctCocktailName = gameSession.getCocktail().getName();
        int totalLetters = correctCocktailName.length();

        Map<Short, Character> revealedLettersMap = revealedLettersService.getRevealedLettersByGameSession(gameSession);
        List<Short> unrevealedPositions = findUnrevealedPositions(correctCocktailName, revealedLettersMap);

        if (unrevealedPositions.size() <= 2) {
            return;
        }

        int lettersToReveal = (int) Math.ceil(totalLetters * 0.1);

        for (int i = 0; i < lettersToReveal && !unrevealedPositions.isEmpty(); i++) {
            Short positionToReveal = unrevealedPositions.remove(random.nextInt(unrevealedPositions.size()));

            RevealedLetters revealedLetter = new RevealedLetters();
            revealedLetter.setGameSession(gameSession);
            revealedLetter.setLetterPosition(positionToReveal);
            revealedLetter.setLetter(correctCocktailName.charAt(positionToReveal));

            revealedLettersService.saveRevealedLetters(revealedLetter);
        }
    }

    /**
     * Finds and returns positions of unrevealed letters in the given cocktail name.
     *
     * @param correctCocktailName the name of the correct cocktail
     * @param revealedLettersMap a map containing the positions of already revealed letters
     * @return a list of short values representing positions of unrevealed letters
     */
    private List<Short> findUnrevealedPositions(String correctCocktailName, Map<Short, Character> revealedLettersMap) {
        List<Short> unrevealedPositions = new ArrayList<>();
        for (short i = 0; i < correctCocktailName.length(); i++) {
            if (correctCocktailName.charAt(i) != ' ' && !revealedLettersMap.containsKey(i)) {
                unrevealedPositions.add(i);
            }
        }
        return unrevealedPositions;
    }

    /**
     * Reveals additional information in the given game session based on the number of remaining attempts.
     * The information revealed depends on the number of attempts left:
     * - 4 attempts left: reveals glass information.
     * - 3 attempts left: reveals category information.
     * - 2 attempts left: reveals picture information.
     * - 1 attempt left: reveals ingredients information.
     *
     * @param gameSession the game session for which to reveal additional information
     * @param attemptsLeft the number of attempts left in the game session
     */
    private void revealAdditionalInfo(GameSession gameSession, Short attemptsLeft) {
        RevealedInfo revealedInfo = revealedInfoService.getRevealedInfoByGameSession(gameSession);

        switch (attemptsLeft) {
            case 4:
                revealedInfo.setGlass(true);
                break;
            case 3:
                revealedInfo.setCategory(true);
                break;
            case 2:
                revealedInfo.setPicture(true);
                break;
            case 1:
                revealedInfo.setIngredients(true);
                break;
            default:
                break;
        }
        revealedInfoService.saveRevealedInfo(revealedInfo);
    }

    /**
     * Marks the given game session as completed and persists the changes.
     *
     * @param gameSession the game session to be completed
     */
    private void completeGameSession(GameSession gameSession) {
        gameSession.setCompleted(true);
        gameSessionService.saveGameSession(gameSession);
    }

    /**
     * Creates a response object containing the current state of the game session.
     *
     * @param gameSession the game session for which to create the response
     * @return a GameSessionResponseDto containing information about the game session
     */
    private GameSessionResponseDto createResponse(GameSession gameSession) {
        GameSessionResponseDto response = new GameSessionResponseDto();

        response.setGameSessionId(gameSession.getGameSessionId());
        response.setInstructions(gameSession.getCocktail().getInstructions());
        response.setAttemptsLeft(gameSession.getAttemptsLeft());
        response.setLetterCount((short) gameSession.getCocktail().getName().length());
        response.setRevealedLetters(revealedLettersService.getRevealedLettersByGameSession(gameSession));
        response.setRevealedInfo(revealedInfoService.getRevealedInfoDtoByGameSession(gameSession));
        response.setScore(gameSession.getPlayer().getScore());

        List<Short> spaceIndexes = findSpaceIndexes(gameSession.getCocktail().getName());
        response.setSpaceIndexes(spaceIndexes);

        return response;
    }

    /**
     * Finds and returns the indexes of spaces in the provided cocktail name.
     *
     * @param cocktailName the name of the cocktail to search for spaces
     * @return a list of short values representing the indexes where spaces are found
     */
    private List<Short> findSpaceIndexes(String cocktailName) {
        List<Short> spaceIndexes = new ArrayList<>();
        for (short i = 0; i < cocktailName.length(); i++) {
            if (cocktailName.charAt(i) == ' ') {
                spaceIndexes.add(i);
            }
        }
        return spaceIndexes;
    }
}
