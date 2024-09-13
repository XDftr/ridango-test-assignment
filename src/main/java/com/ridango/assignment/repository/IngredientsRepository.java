package com.ridango.assignment.repository;

import com.ridango.assignment.entity.Cocktail;
import com.ridango.assignment.entity.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {
    List<Ingredients> findAllByCocktail(Cocktail cocktail);
}
