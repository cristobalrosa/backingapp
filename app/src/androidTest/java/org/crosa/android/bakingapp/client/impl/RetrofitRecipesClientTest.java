package org.crosa.android.bakingapp.client.impl;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.crosa.android.bakingapp.client.exceptions.RecipesClientException;
import org.crosa.android.bakingapp.model.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RetrofitRecipesClientTest  {

    @Test
    public void testDownloadRecipesOnline() throws RecipesClientException {
        RetrofitRecipesClientImpl client = new RetrofitRecipesClientImpl();
        List<Recipe> recipeList = client.getRecipes();
        assertNotNull(recipeList);
        assertTrue(recipeList.size() > 0);
    }

    @Test(expected = RecipesClientException.class)
    public void testDownloadRecipesOffline() throws RecipesClientException {
        RetrofitRecipesClientImpl client = new RetrofitRecipesClientImpl("https://fakeurlortesting.cloudfront.net/");
        List<Recipe> recipeList = client.getRecipes();
    }
}