package com.ridango.assignment.service;

import com.ridango.assignment.entity.GameSession;
import com.ridango.assignment.entity.RevealedLetters;
import com.ridango.assignment.repository.RevealedLettersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RevealedLettersService {
    private final RevealedLettersRepository revealedLettersRepository;

    /**
     * Fetches and returns a map of revealed letter positions and their corresponding characters
     * for a given game session.
     *
     * @param gameSession the game session for which to fetch revealed letters
     * @return a map containing the positions and characters of revealed letters
     */
    public Map<Short, Character> getRevealedLettersByGameSession(GameSession gameSession) {
        List<RevealedLetters> revealedLetters = revealedLettersRepository.findAllByGameSession(gameSession);
        Map<Short, Character> revealedLettersMap = new HashMap<>();
        for (RevealedLetters revealedLetter : revealedLetters) {
            revealedLettersMap.put(revealedLetter.getLetterPosition(), revealedLetter.getLetter());
        }

        return revealedLettersMap;
    }

    /**
     * Saves the specified revealed letters entity.
     *
     * @param revealedLetters the revealed letters entity to save
     */
    public void saveRevealedLetters(RevealedLetters revealedLetters) {
        revealedLettersRepository.save(revealedLetters);
    }
}
