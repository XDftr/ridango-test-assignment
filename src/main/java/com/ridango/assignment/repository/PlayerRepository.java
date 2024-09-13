package com.ridango.assignment.repository;

import com.ridango.assignment.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> getPlayerByUsername(String username);
}
