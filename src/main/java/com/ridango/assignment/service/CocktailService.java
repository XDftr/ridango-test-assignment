package com.ridango.assignment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridango.assignment.dto.CocktailDto;
import com.ridango.assignment.dto.DrinksDto;
import com.ridango.assignment.entity.Cocktail;
import com.ridango.assignment.entity.Ingredients;
import com.ridango.assignment.exception.CocktailApiException;
import com.ridango.assignment.repository.CocktailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CocktailService {
    private final CocktailRepository cocktailRepository;
    private final IngredientsService ingredientService;

    /**
     * Fetches a random cocktail from an external API and saves it to the local database if it does not already exist.
     *
     * @return a random Cocktail object, either fetched from the external API or retrieved from the local database
     * @throws CocktailApiException if there is an issue fetching the cocktail from the external API or parsing the API response
     */
    @Transactional
    public Cocktail getRandomCocktail() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.thecocktaildb.com/api/json/v1/1/random.php"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();

            ObjectMapper objectMapper = new ObjectMapper();

            DrinksDto drinksDto = objectMapper.readValue(jsonResponse, DrinksDto.class);

            if (drinksDto.getDrinks() != null && !drinksDto.getDrinks().isEmpty()) {
                CocktailDto cocktailDto = drinksDto.getDrinks().getFirst();

                Optional<Cocktail> cocktailInDatabase = cocktailRepository.getCocktailByApiId(cocktailDto.getIdDrink());
                if (cocktailInDatabase.isPresent()) {
                    return cocktailInDatabase.get();
                }

                Cocktail cocktail = new Cocktail();
                cocktail.setApiId(cocktailDto.getIdDrink());
                cocktail.setCategory(cocktailDto.getStrCategory());
                cocktail.setGlass(cocktailDto.getStrGlass());
                cocktail.setName(cocktailDto.getStrDrink().toUpperCase());
                cocktail.setInstructions(cocktailDto.getStrInstructions());
                cocktail.setImageUrl(cocktailDto.getStrDrinkThumb());

                Cocktail savedCocktail = cocktailRepository.save(cocktail);

                List<String> ingredientsWithMeasures = cocktailDto.getIngredientsWithMeasures();

                for (String ingredientWithMeasure : ingredientsWithMeasures) {
                    Ingredients ingredients = new Ingredients();
                    ingredients.setCocktail(savedCocktail);
                    ingredients.setIngredient(ingredientWithMeasure);

                    ingredientService.saveIngredients(ingredients);
                }

                return savedCocktail;
            } else {
                throw new CocktailApiException("No drinks found in the API response");
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CocktailApiException("Failed to fetch random cocktail from external API");
        }
    }
}
