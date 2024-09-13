package com.ridango.assignment.service;

import com.ridango.assignment.entity.Ingredients;
import com.ridango.assignment.repository.IngredientsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class IngredientsService {
    private final IngredientsRepository ingredientRepository;

    /**
     * Saves the provided ingredients entity into the repository.
     *
     * @param ingredients the Ingredients entity to be saved
     */
    public void saveIngredients(Ingredients ingredients) {
        ingredientRepository.save(ingredients);
    }
}
