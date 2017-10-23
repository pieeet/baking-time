package com.rocdev.android.bakingtime.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rocdev.android.bakingtime.R;
import com.rocdev.android.bakingtime.models.Recipe;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RECIPE = "recipe";

    private Recipe mRecipe;


    public IngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipe the recipe.
     * @return A new instance of fragment IngredientsFragment.
     */
    public static IngredientsFragment newInstance(Recipe recipe) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_RECIPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        RecyclerView ingredientsRecyclerView = rootView.findViewById(R.id.rv_ingredients_list);
        Context context = getContext();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        IngredientsAdapter adapter = new IngredientsAdapter(getContext(), mRecipe.getIngredients());
        ingredientsRecyclerView.setAdapter(adapter);
        return rootView;
    }

}
