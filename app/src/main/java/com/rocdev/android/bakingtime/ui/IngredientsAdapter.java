package com.rocdev.android.bakingtime.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rocdev.android.bakingtime.R;
import com.rocdev.android.bakingtime.models.Ingredient;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by piet on 23-10-17.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {



    private static final String TAG = IngredientsAdapter.class.getSimpleName();

    private Context mContext;
    private List<Ingredient> mIngredients;


    IngredientsAdapter(Context context, List<Ingredient> ingredients) {
        this.mContext = context;
        this.mIngredients = ingredients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View ingredientView = inflater.inflate(R.layout.ingredient_list_item, parent, false);
        return new ViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);
        holder.nameTextView.setText(ingredient.getName());
        double quantity = ingredient.getQuantity();
        if (quantity % 1 == 0) {
            int q = (int) quantity;
            holder.quantityTextView.setText(q + " " +
                    Ingredient.getNormalMeasure(mContext, ingredient.getMeasure()));
        } else {
            holder.quantityTextView.setText(quantity + " " +
                    Ingredient.getNormalMeasure(mContext, ingredient.getMeasure()));
        }
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null) return 0;
        return mIngredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView quantityTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_ingredient_name);
            quantityTextView = itemView.findViewById(R.id.tv_quantity);
        }
    }
}
