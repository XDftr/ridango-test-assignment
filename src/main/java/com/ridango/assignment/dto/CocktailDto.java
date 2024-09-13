package com.ridango.assignment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CocktailDto {
    private String idDrink;
    private String strDrink;
    private String strInstructions;
    private String strCategory;
    private String strGlass;
    private String strDrinkThumb;

    private String strIngredient1;
    private String strIngredient2;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strIngredient6;
    private String strIngredient7;
    private String strIngredient8;
    private String strIngredient9;
    private String strIngredient10;
    private String strIngredient11;
    private String strIngredient12;
    private String strIngredient13;
    private String strIngredient14;
    private String strIngredient15;

    private String strMeasure1;
    private String strMeasure2;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure5;
    private String strMeasure6;
    private String strMeasure7;
    private String strMeasure8;
    private String strMeasure9;
    private String strMeasure10;
    private String strMeasure11;
    private String strMeasure12;
    private String strMeasure13;
    private String strMeasure14;
    private String strMeasure15;

    public List<String> getIngredientsWithMeasures() {
        List<String> ingredientsWithMeasures = new ArrayList<>();

        for (int i = 1; i <= 15; i++) {
            String ingredient = getIngredient(i);
            String measure = getMeasure(i);

            if (ingredient == null || ingredient.isEmpty()) {
                break;
            }

            if (measure != null && !measure.isEmpty()) {
                ingredientsWithMeasures.add(ingredient + " - " + measure);
            } else {
                ingredientsWithMeasures.add(ingredient);
            }
        }

        return ingredientsWithMeasures;
    }

    private String getIngredient(int index) {
        return switch (index) {
            case 1 -> strIngredient1;
            case 2 -> strIngredient2;
            case 3 -> strIngredient3;
            case 4 -> strIngredient4;
            case 5 -> strIngredient5;
            case 6 -> strIngredient6;
            case 7 -> strIngredient7;
            case 8 -> strIngredient8;
            case 9 -> strIngredient9;
            case 10 -> strIngredient10;
            case 11 -> strIngredient11;
            case 12 -> strIngredient12;
            case 13 -> strIngredient13;
            case 14 -> strIngredient14;
            case 15 -> strIngredient15;
            default -> null;
        };
    }

    private String getMeasure(int index) {
        return switch (index) {
            case 1 -> strMeasure1;
            case 2 -> strMeasure2;
            case 3 -> strMeasure3;
            case 4 -> strMeasure4;
            case 5 -> strMeasure5;
            case 6 -> strMeasure6;
            case 7 -> strMeasure7;
            case 8 -> strMeasure8;
            case 9 -> strMeasure9;
            case 10 -> strMeasure10;
            case 11 -> strMeasure11;
            case 12 -> strMeasure12;
            case 13 -> strMeasure13;
            case 14 -> strMeasure14;
            case 15 -> strMeasure15;
            default -> null;
        };
    }
}
