package com.ridango.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridango.assignment.dto.CocktailGuessRequestDto;
import com.ridango.assignment.dto.CreateGameRequestDto;
import com.ridango.assignment.dto.GameSessionResponseDto;
import com.ridango.assignment.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameService gameService;

    @Test
    void createGame() throws Exception {
        GameSessionResponseDto mockResponse = new GameSessionResponseDto();
        mockResponse.setGameSessionId(1L);
        mockResponse.setInstructions("Test Instructions");
        mockResponse.setAttemptsLeft((short) 5);
        mockResponse.setScore(10);

        when(gameService.createGame(anyString())).thenReturn(mockResponse);

        CreateGameRequestDto request = new CreateGameRequestDto();
        request.setUsername("testUser");

        mockMvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameSessionId").value(1L))
                .andExpect(jsonPath("$.instructions").value("Test Instructions"))
                .andExpect(jsonPath("$.attemptsLeft").value(5))
                .andExpect(jsonPath("$.score").value(10));
    }

    @Test
    void guessCocktail() throws Exception {
        GameSessionResponseDto mockResponse = new GameSessionResponseDto();
        mockResponse.setGameSessionId(1L);
        mockResponse.setInstructions("Test Instructions");
        mockResponse.setAttemptsLeft((short) 4);
        mockResponse.setScore(12);

        when(gameService.guessCocktail(anyString(), anyLong())).thenReturn(mockResponse);

        CocktailGuessRequestDto request = new CocktailGuessRequestDto();
        request.setCocktailName("Mojito");

        mockMvc.perform(post("/api/game/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameSessionId").value(1L))
                .andExpect(jsonPath("$.instructions").value("Test Instructions"))
                .andExpect(jsonPath("$.attemptsLeft").value(4))
                .andExpect(jsonPath("$.score").value(12));
    }
}