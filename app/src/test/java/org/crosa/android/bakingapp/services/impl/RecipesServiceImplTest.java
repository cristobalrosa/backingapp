package org.crosa.android.bakingapp.services.impl;


import android.content.Context;
import android.content.res.AssetManager;

import org.crosa.android.bakingapp.client.RecipesClient;
import org.crosa.android.bakingapp.client.exceptions.RecipesClientException;
import org.crosa.android.bakingapp.model.Ingredient;
import org.crosa.android.bakingapp.model.Recipe;
import org.crosa.android.bakingapp.model.Step;
import org.crosa.android.bakingapp.services.RecipesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipesServiceImplTest {

    @Mock
    RecipesClient client;

    @Mock
    Context mockContext;
    @Mock
    AssetManager mockAssetManager;

    RecipesService recipesService;

    @Before
    public void setUp() {
        recipesService = new RecipesServiceImpl(client, mockContext);
    }


    @Test
    public void getRecipesUsingClient() throws RecipesClientException {
        // Arrange
        when(client.getRecipes()).thenReturn(getFakeRecipeList(5));

        // Act
        List<Recipe> recipes = recipesService.getRecipes();

        // Assert
        assertEquals(5, recipes.size());
        verify(client, times(1)).getRecipes();
    }

    @Test
    public void getRecipesUsingLocalFileFails() throws RecipesClientException, IOException {
        // Arrange
        when(client.getRecipes()).thenThrow(new RecipesClientException("error"));
        when(mockContext.getAssets()).thenReturn(mockAssetManager);
        when(mockAssetManager.open(anyString())).thenThrow(new IOException("Not found"));
        when(mockContext.getString(anyInt())).thenReturn("path");

        // Act
        List<Recipe> recipes = recipesService.getRecipes();

        // Assert
        assertEquals(0, recipes.size());
        verify(client, times(1)).getRecipes();
        verify(mockContext, times(1)).getAssets();
        verify(mockAssetManager, times(1)).open(anyString());
    }

    @Test
    public void getRecipesUsingLocalFileOk() throws RecipesClientException, IOException {
        // Arrange
        when(client.getRecipes()).thenThrow(new RecipesClientException("error"));
        when(mockContext.getAssets()).thenReturn(mockAssetManager);
        when(mockAssetManager.open(anyString())).thenReturn(new ByteArrayInputStream(getRecipes().getBytes(Charset.forName("UTF-8"))));
        when(mockContext.getString(anyInt())).thenReturn("path");

        // Act
        List<Recipe> recipes = recipesService.getRecipes();

        // Assert
        assertEquals(1, recipes.size());
        assertEquals("1", recipes.get(0).getId());
        assertEquals("Nutella Pie", recipes.get(0).getName());
        verify(client, times(1)).getRecipes();
        verify(mockContext, times(1)).getAssets();
        verify(mockAssetManager, times(1)).open(anyString());
    }

    private List<Recipe> getFakeRecipeList(int numberOfRecipes) {
        List<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < numberOfRecipes; i++) {
            Recipe recipe = new Recipe("id_" + i, "recipe_" + i, new ArrayList<Ingredient>(), new ArrayList<Step>(), 5, "");
            recipes.add(recipe);
        }
        return recipes;
    }

    private String getRecipes() {
        return "[  {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Nutella Pie\",\n" +
                "    \"ingredients\": [\n" +
                "      {\n" +
                "        \"quantity\": 2,\n" +
                "        \"measure\": \"CUP\",\n" +
                "        \"ingredient\": \"Graham Cracker crumbs\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"quantity\": 6,\n" +
                "        \"measure\": \"TBLSP\",\n" +
                "        \"ingredient\": \"unsalted butter, melted\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"quantity\": 0.5,\n" +
                "        \"measure\": \"CUP\",\n" +
                "        \"ingredient\": \"granulated sugar\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"quantity\": 1.5,\n" +
                "        \"measure\": \"TSP\",\n" +
                "        \"ingredient\": \"salt\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"quantity\": 5,\n" +
                "        \"measure\": \"TBLSP\",\n" +
                "        \"ingredient\": \"vanilla\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"quantity\": 1,\n" +
                "        \"measure\": \"K\",\n" +
                "        \"ingredient\": \"Nutella or other chocolate-hazelnut spread\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"quantity\": 500,\n" +
                "        \"measure\": \"G\",\n" +
                "        \"ingredient\": \"Mascapone Cheese(room temperature)\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"quantity\": 1,\n" +
                "        \"measure\": \"CUP\",\n" +
                "        \"ingredient\": \"heavy cream(cold)\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"quantity\": 4,\n" +
                "        \"measure\": \"OZ\",\n" +
                "        \"ingredient\": \"cream cheese(softened)\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"steps\": [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"shortDescription\": \"Recipe Introduction\",\n" +
                "        \"description\": \"Recipe Introduction\",\n" +
                "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4\",\n" +
                "        \"thumbnailURL\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"shortDescription\": \"Starting prep\",\n" +
                "        \"description\": \"1. Preheat the oven to 350\\u00b0F. Butter a 9\\\" deep dish pie pan.\",\n" +
                "        \"videoURL\": \"\",\n" +
                "        \"thumbnailURL\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"shortDescription\": \"Prep the cookie crust.\",\n" +
                "        \"description\": \"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.\",\n" +
                "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4\",\n" +
                "        \"thumbnailURL\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 3,\n" +
                "        \"shortDescription\": \"Press the crust into baking form.\",\n" +
                "        \"description\": \"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.\",\n" +
                "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4\",\n" +
                "        \"thumbnailURL\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 4,\n" +
                "        \"shortDescription\": \"Start filling prep\",\n" +
                "        \"description\": \"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.\",\n" +
                "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4\",\n" +
                "        \"thumbnailURL\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 5,\n" +
                "        \"shortDescription\": \"Finish filling prep\",\n" +
                "        \"description\": \"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.\",\n" +
                "        \"videoURL\": \"\",\n" +
                "        \"thumbnailURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 6,\n" +
                "        \"shortDescription\": \"Finishing Steps\",\n" +
                "        \"description\": \"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!\",\n" +
                "        \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4\",\n" +
                "        \"thumbnailURL\": \"\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"servings\": 8,\n" +
                "    \"image\": \"\"\n" +
                "  }]";
    }
}