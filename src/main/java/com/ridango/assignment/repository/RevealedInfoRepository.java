package com.ridango.assignment.repository;

import com.ridango.assignment.entity.GameSession;
import com.ridango.assignment.entity.RevealedInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RevealedInfoRepository extends JpaRepository<RevealedInfo, Long> {
    Optional<RevealedInfo> getRevealedInfoByGameSession(GameSession gameSession);
}
