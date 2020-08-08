package org.crosa.android.bakingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.crosa.android.bakingapp.R;
import org.crosa.android.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientRowAdapter extends BaseAdapter {

    private final List<Ingredient> ingredients;
    private final Context context;

    public IngredientRowAdapter(Context context, List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ingredients != null ? ingredients.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ingredient_row, parent, false);
        }

        Ingredient ingredient = (Ingredient) getItem(position);
        ((TextView) convertView.findViewById(R.id.ingredient_name)).setText(ingredient.getIngredient());
        ((TextView) convertView.findViewById(R.id.ingredient_quantity)).setText(String.valueOf(ingredient.getQuantity()));
        ((TextView) convertView.findViewById(R.id.ingredient_measure)).setText(ingredient.getMeasure());
        return convertView;
    }
}
