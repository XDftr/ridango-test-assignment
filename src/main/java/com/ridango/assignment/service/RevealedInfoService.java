package com.ridango.assignment.service;

import com.ridango.assignment.dto.RevealedInfoDto;
import com.ridango.assignment.entity.GameSession;
import com.ridango.assignment.entity.Ingredients;
import com.ridango.assignment.entity.RevealedInfo;
import com.ridango.assignment.repository.RevealedInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RevealedInfoService {
    private final RevealedInfoRepository revealedInfoRepository;

    /**
     * Retrieves the RevealedInfoDto for a given GameSession. If no revealed information
     * exists for the specified game session, initializes and saves new revealed information.
     *
     * @param gameSession The GameSession for which to retrieve the revealed information.
     * @return The corresponding RevealedInfoDto containing the revealed information details.
     */
    public RevealedInfoDto getRevealedInfoDtoByGameSession(GameSession gameSession) {
        RevealedInfoDto revealedInfoDto = new RevealedInfoDto();
        Optional<RevealedInfo> revealedInfoOptional = revealedInfoRepository.getRevealedInfoByGameSession(gameSession);

        if (revealedInfoOptional.isEmpty()) {
            RevealedInfo newRevealedInfo = new RevealedInfo();
            newRevealedInfo.setGameSession(gameSession);
            saveRevealedInfo(newRevealedInfo);
            return revealedInfoDto;
        }

        RevealedInfo revealedInfo = revealedInfoOptional.get();

        revealedInfoDto.setCategory(revealedInfo.isCategory() ? gameSession.getCocktail().getCategory() : null);
        revealedInfoDto.setGlass(revealedInfo.isGlass() ? gameSession.getCocktail().getGlass() : null);
        revealedInfoDto.setPicture(revealedInfo.isPicture() ? gameSession.getCocktail().getImageUrl() : null);

        if (revealedInfo.isIngredients()) {
            List<String> ingredientsList = gameSession
                    .getCocktail()
                    .getIngredients()
                    .stream()
                    .map(Ingredients::getIngredient)
                    .toList();
            revealedInfoDto.setIngredients(ingredientsList);
        }

        return revealedInfoDto;
    }

    /**
     * Retrieves the revealed information for a given game session. If no revealed
     * information exists for the specified game session, initializes and saves new
     * revealed information.
     *
     * @param gameSession The game session for which to retrieve the revealed information.
     * @return The corresponding revealed information for the given game session.
     */
    public RevealedInfo getRevealedInfoByGameSession(GameSession gameSession) {
        return revealedInfoRepository.getRevealedInfoByGameSession(gameSession).orElseGet(() -> {
            RevealedInfo newRevealedInfo = new RevealedInfo();
            newRevealedInfo.setGameSession(gameSession);
            saveRevealedInfo(newRevealedInfo);
            return newRevealedInfo;
        });
    }

    /**
     * Saves the provided RevealedInfo entity to the repository.
     *
     * @param revealedInfo The RevealedInfo entity to save.
     */
    public void saveRevealedInfo(RevealedInfo revealedInfo) {
        revealedInfoRepository.save(revealedInfo);
    }
}
