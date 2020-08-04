package org.crosa.android.bakingapp.services.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.crosa.android.bakingapp.R;
import org.crosa.android.bakingapp.client.RecipesClient;
import org.crosa.android.bakingapp.client.exceptions.RecipesClientException;
import org.crosa.android.bakingapp.client.impl.RetrofitRecipesClient;
import org.crosa.android.bakingapp.services.RecipesService;
import org.crosa.android.bakingapp.model.Recipe;
import org.crosa.android.bakingapp.utils.Utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecipesServiceImpl implements RecipesService {
    private final String TAG = RecipesServiceImpl.class.getSimpleName();
    private static final String dataPath = "";
    private final RecipesClient client;
    private final Context context;
    private final Gson gson;

    public RecipesServiceImpl(RecipesClient client, Context context) {
        this.client = client;
        this.context = context;
        this.gson = new Gson();
    }

    @Override
    public List<Recipe> getRecipes() {
        try {
            return client.getRecipes();
        } catch (RecipesClientException e) {
            Log.w(TAG, e);
            Log.i(TAG, "Trying to load recipes from local file system");
        }
        return loadFromFileSystem();
    }

    private List<Recipe> loadFromFileSystem() {
        try {
            String jsonFileString = Utils.getJsonFromAssets(context, context.getString(R.string.default_recipes_file));
            if (jsonFileString != null) {
                Type listRecipeType = new TypeToken<List<Recipe>>() {
                }.getType();
                return gson.fromJson(jsonFileString, listRecipeType);
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to read local file contents.");
        }
        return new ArrayList<>();
    }
}
