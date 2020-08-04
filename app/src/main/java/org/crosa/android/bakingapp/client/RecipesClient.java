package org.crosa.android.bakingapp.client;

import org.crosa.android.bakingapp.client.exceptions.RecipesClientException;
import org.crosa.android.bakingapp.model.Recipe;

import java.util.List;

public interface RecipesClient {
    /**
     * Returns a list of recipes
     * @return
     */
    List<Recipe> getRecipes() throws RecipesClientException;
}
