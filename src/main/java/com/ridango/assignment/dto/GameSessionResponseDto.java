package com.ridango.assignment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GameSessionResponseDto {
    private Long gameSessionId;
    private String instructions;
    private Short attemptsLeft;
    private Integer score;
    private Short letterCount;
    private Map<Short, Character> revealedLetters;
    private RevealedInfoDto revealedInfo;
    private List<Short> spaceIndexes;
}
