package com.ridango.assignment.controller;

import com.ridango.assignment.dto.CocktailGuessRequestDto;
import com.ridango.assignment.dto.CreateGameRequestDto;
import com.ridango.assignment.dto.GameSessionResponseDto;
import com.ridango.assignment.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {
    private final GameService gameService;

    @PostMapping
    public GameSessionResponseDto createGame(@RequestBody CreateGameRequestDto createGameRequestDto) {
        return gameService.createGame(createGameRequestDto.getUsername());
    }

    @PostMapping("/{gameSessionId}")
    public GameSessionResponseDto guessCocktail(
            @RequestBody CocktailGuessRequestDto cocktailGuessRequestDto,
            @PathVariable Long gameSessionId) {
        return gameService.guessCocktail(cocktailGuessRequestDto.getCocktailName(), gameSessionId);
    }
}
