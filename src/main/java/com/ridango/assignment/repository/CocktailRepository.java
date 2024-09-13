package com.ridango.assignment.repository;

import com.ridango.assignment.entity.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
    Optional<Cocktail> getCocktailByApiId(String apiId);
}
