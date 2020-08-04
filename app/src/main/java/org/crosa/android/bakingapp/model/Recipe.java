package org.crosa.android.bakingapp.model;

import java.util.List;

import lombok.Value;

@Value
public class Recipe {
    private String id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;
}
