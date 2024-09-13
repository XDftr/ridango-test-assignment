package com.ridango.assignment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "revealed_letters")
public class RevealedLetters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "revealed_letters_id")
    private Long revealedLettersId;

    @ManyToOne
    @JoinColumn(name = "game_session_id", nullable = false)
    private GameSession gameSession;

    @Column(name = "letter_position", nullable = false)
    private Short letterPosition;

    @Column(name = "letter", nullable = false)
    private Character letter;
}
