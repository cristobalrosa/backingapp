package org.crosa.android.bakingapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.crosa.android.bakingapp.R;
import org.crosa.android.bakingapp.model.Step;

import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsAdapterViewHolder> {
    private List<Step> recipeSteps;
    private final RecipeStepsAdapter.RecipeStepAdapterOnClickHandler clickHandler;

    public interface RecipeStepAdapterOnClickHandler {
        void onClick(Step recipeStep);
    }

    public RecipeStepsAdapter(RecipeStepAdapterOnClickHandler handler) {
        this.clickHandler = handler;
    }

    @NonNull
    @Override
    public RecipeStepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_step;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeStepsAdapter.RecipeStepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepsAdapterViewHolder holder, int position) {
        Step recipeStep = recipeSteps.get(position);
        holder.stepNumberTV.setText(recipeStep.getId());
        holder.stepDescriptionTV.setText(recipeStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return recipeSteps != null ? recipeSteps.size() : 0;
    }

    public void setRecipeSteps(List<Step> recipeSteps) {
        this.recipeSteps = recipeSteps;
        notifyDataSetChanged();
    }

    public class RecipeStepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView stepNumberTV;
        private final TextView stepDescriptionTV;

        private final String TAG = RecipeStepsAdapterViewHolder.class.getSimpleName();

        public RecipeStepsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            stepNumberTV = itemView.findViewById(R.id.recipe_step_number_tv);
            stepDescriptionTV = itemView.findViewById(R.id.recipe_step_description_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Log.d(TAG, "Clicked on " + adapterPosition);
            clickHandler.onClick(recipeSteps.get(adapterPosition));
        }
    }
}
