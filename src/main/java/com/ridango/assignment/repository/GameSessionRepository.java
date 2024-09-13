package com.ridango.assignment.repository;

import com.ridango.assignment.entity.Cocktail;
import com.ridango.assignment.entity.GameSession;
import com.ridango.assignment.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    Optional<GameSession> getGameSessionByPlayerAndIsCompleted(Player player, boolean isCompleted);

    @Query("SELECT gs.cocktail FROM GameSession gs WHERE gs.player = :player AND gs.isCompleted = true")
    List<Cocktail> findGuessedCocktailsByPlayer(@Param("player") Player player);
}
