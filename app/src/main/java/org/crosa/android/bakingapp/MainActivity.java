package org.crosa.android.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.crosa.android.bakingapp.adapters.RecipesAdapter;
import org.crosa.android.bakingapp.model.Recipe;
import org.crosa.android.bakingapp.services.RecipesService;
import org.crosa.android.bakingapp.services.ServiceLocator;
import org.crosa.android.bakingapp.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickHandler {

    private RecipesService recipesService;
    private RecipesAdapter recipesAdapter;
    private RecyclerView recyclerView;
    private TextView errorMessage;
    private ProgressBar loadingIndicator;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipesService = ServiceLocator.getInstance(getApplicationContext()).getRecipesService();
        errorMessage = findViewById(R.id.tv_error_message_display);

        recyclerView = findViewById(R.id.recipe_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        recipesAdapter = new RecipesAdapter(this);
        recyclerView.setAdapter(recipesAdapter);
        loadingIndicator = findViewById(R.id.pb_loading_indicator);

        loadRecipes();
    }


    /**
     * Retrieve the number of columns to display.
     * <p>
     * NOTE: Suggestion in stage 1 review.
     *
     * @return The number of columns to display
     */
    private int getNumberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }

    @Override
    public void onClick(Recipe recipe) {
        Log.d(TAG, "Clicked on " + recipe.getName());
    }

    private void loadRecipes() {
        showMovieDataView();
        new GetRecipesTask(recipesService).execute();

    }

    private void showMovieDataView() {
        errorMessage.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        recyclerView.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    public class GetRecipesTask extends AsyncTask<Void, Void, List<Recipe>> {
        private final String TAG = GetRecipesTask.class.getSimpleName();
        private final RecipesService recipesService;

        public GetRecipesTask(RecipesService recipesService) {
            this.recipesService = recipesService;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            return recipesService.getRecipes();
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            loadingIndicator.setVisibility(View.INVISIBLE);
            if (recipes != null) {
                showMovieDataView();
                recipesAdapter.setRecipes(recipes);
            } else {
                showErrorMessage();
            }
        }
    }
}