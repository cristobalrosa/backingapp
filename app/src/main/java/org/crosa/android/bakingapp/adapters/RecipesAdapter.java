package org.crosa.android.bakingapp.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.crosa.android.bakingapp.R;
import org.crosa.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {

    private List<Recipe> recipes;
    private final RecipesAdapterOnClickHandler clickHandler;

    public RecipesAdapter(RecipesAdapterOnClickHandler handler) {
        clickHandler = handler;
    }

    public interface RecipesAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    @NonNull
    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapterViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        String recipeImagePath = recipe.getImage();
        if (!TextUtils.isEmpty(recipeImagePath)) {
            // A default image will be shown otherwise.
            Picasso.get().load(recipeImagePath).into(holder.recipeImageIV);
        }
        holder.recipeNameTV.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView recipeImageIV;
        private final TextView recipeNameTV;

        private final String TAG = RecipesAdapterViewHolder.class.getSimpleName();

        public RecipesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImageIV = itemView.findViewById(R.id.recipe_image_iv);
            recipeNameTV = itemView.findViewById(R.id.recipe_name_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Log.d(TAG, "Clicked on " + adapterPosition);
            clickHandler.onClick(recipes.get(adapterPosition));
        }
    }
}
