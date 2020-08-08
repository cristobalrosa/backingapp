package org.crosa.android.bakingapp.model;

import java.io.Serializable;
import java.util.List;

import lombok.Value;

@Value
public class Recipe implements Serializable {
    private String id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;
}
