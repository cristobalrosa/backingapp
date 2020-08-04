package org.crosa.android.bakingapp.model;

import lombok.Data;
import lombok.Value;

@Value
public class Ingredient {
    private double quantity;
    private String measure;
    private String ingredient;
}
