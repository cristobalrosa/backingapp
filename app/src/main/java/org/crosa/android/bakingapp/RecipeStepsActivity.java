package org.crosa.android.bakingapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import org.crosa.android.bakingapp.fragments.RecipeStepsFragment;
import org.crosa.android.bakingapp.model.Recipe;

public class RecipeStepsActivity extends AppCompatActivity {
    private boolean mTwoPanels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
        // Only cre ate new fragments when there is no previously saved state
        if(savedInstanceState == null) {
            // Create a new head BodyPartFragment
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();

            // Get the correct index to access in the array of head images from the intent
            // Set the default value to 0
            Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");
            recipeStepsFragment.setRecipe(recipe);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.recipe_steps_container, recipeStepsFragment)
                    .commit();
        }
    }
}
