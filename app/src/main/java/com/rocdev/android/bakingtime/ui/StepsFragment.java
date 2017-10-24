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
 * Use the {@link StepsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsFragment extends Fragment  {

    private static final String ARG_RECIPE = "recipe";
    private Recipe mRecipe;


    public StepsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param recipe the recipe with the steps in it
     * @return A new instance of fragment StepsFragment.
     */
    public static StepsFragment newInstance(Recipe recipe) {
        StepsFragment fragment = new StepsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        if (savedInstanceState == null) {
            RecyclerView stepsRecyclerView = rootView.findViewById(R.id.rv_steps_list);
            Context context = getContext();
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            stepsRecyclerView.setLayoutManager(layoutManager);
            StepsAdapter.OnItemClickedListener callBack;
            if (context instanceof StepsAdapter.OnItemClickedListener) {
                callBack = (StepsAdapter.OnItemClickedListener) context;
            } else {
                throw new RuntimeException(context.toString() + " must implement " +
                        "StepsAdapter.OnItemClickedListener");
            }
            StepsAdapter adapter = new StepsAdapter(context, callBack,
                    mRecipe.getSteps());
            stepsRecyclerView.setAdapter(adapter);

        }
        return rootView;
    }

}
