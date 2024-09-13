package com.ridango.assignment.repository;

import com.ridango.assignment.entity.GameSession;
import com.ridango.assignment.entity.RevealedLetters;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RevealedLettersRepository extends JpaRepository<RevealedLetters, Long> {
    List<RevealedLetters> findAllByGameSession(GameSession gameSession);
}
