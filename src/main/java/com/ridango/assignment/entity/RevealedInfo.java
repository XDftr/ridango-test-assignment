package com.ridango.assignment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "revealed_info")
public class RevealedInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "revealed_info_id")
    private Long revealedInfoId;

    @OneToOne
    @JoinColumn(name = "game_session_id", nullable = false)
    private GameSession gameSession;

    @Column(name = "category", nullable = false)
    private boolean category = false;

    @Column(name = "glass", nullable = false)
    private boolean glass = false;

    @Column(name = "ingredients", nullable = false)
    private boolean ingredients = false;

    @Column(name = "picture", nullable = false)
    private boolean picture = false;
}
