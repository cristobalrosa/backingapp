package org.crosa.android.bakingapp.model;

import java.io.Serializable;

import lombok.Data;
import lombok.Value;

@Value
public class Ingredient  implements Serializable {
    private double quantity;
    private String measure;
    private String ingredient;
}
