package org.crosa.android.bakingapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.crosa.android.bakingapp.R;
import org.crosa.android.bakingapp.adapters.IngredientRowAdapter;
import org.crosa.android.bakingapp.adapters.RecipeStepsAdapter;
import org.crosa.android.bakingapp.model.Recipe;
import org.crosa.android.bakingapp.model.Step;

public class RecipeStepsFragment extends Fragment implements RecipeStepsAdapter.RecipeStepAdapterOnClickHandler {

    private static final String TAG = RecipeStepsFragment.class.getSimpleName();
    private ListView ingredientListLV;
    private RecyclerView recipeStepsRV;
    private Recipe recipe;


    public RecipeStepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        recipeStepsRV = view.findViewById(R.id.recipe_steps_rv);
        recipeStepsRV.setHasFixedSize(true);
        recipeStepsRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(this);
        ingredientListLV = view.findViewById(R.id.recipe_list_of_ingredients);
        if (recipe != null) {
            recipeStepsAdapter.setRecipeSteps(recipe.getSteps());
            //TODO change this.
            ingredientListLV.setAdapter(new IngredientRowAdapter(getActivity(), recipe.getIngredients()));
        }
        recipeStepsRV.setAdapter(recipeStepsAdapter);
        return view;
    }

    @Override
    public void onClick(Step recipeStep) {
        Log.d(TAG, "Step " + recipeStep.getId());
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
