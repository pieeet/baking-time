package com.rocdev.android.bakingtime.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rocdev.android.bakingtime.R;
import com.rocdev.android.bakingtime.models.Recipe;
import com.rocdev.android.bakingtime.models.Step;

public class StepsActivity extends AppCompatActivity implements
        StepsAdapter.OnItemClickedListener,
StepDetailFragment.OnFragmentInteractionListener {

    private Recipe mRecipe;
    private boolean isTwoPane;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        isTwoPane = findViewById(R.id.step_detail_fragment_container ) != null;
        mRecipe = getIntent().getParcelableExtra(getString(R.string.key_recipe));
        initNavigation();
        initfragments();
    }

    private void initfragments() {
        StepsFragment fragment = StepsFragment.newInstance(mRecipe);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager
                .beginTransaction()
                .replace(R.id.steps_fragment_container, fragment)
                .commit();
    }

    private void initNavigation() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mRecipe.getName());
        }
    }

    @Override
    public void onStepClicked(Step step) {
        if (isTwoPane) {
            StepDetailFragment fragment = StepDetailFragment.newInstance(mRecipe, getStepPosition(step));
            mFragmentManager.beginTransaction()
                    .replace(R.id.step_detail_fragment_container, fragment)
                    .commit();

        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(getString(R.string.key_recipe), mRecipe);
            intent.putExtra(getString(R.string.key_step_position), getStepPosition(step));
            startActivity(intent);
        }

    }

    private int getStepPosition(Step step) {
        for (int i = 0; i < mRecipe.getSteps().size(); i++) {
            if (step.getId() == mRecipe.getSteps().get(i).getId() ) return i;
        }
        return 0;
    }

    @Override
    public void onIngredientsClicked() {
        if (isTwoPane) {
            IngredientsFragment ingredientsFragment = IngredientsFragment.newInstance(mRecipe);
            mFragmentManager.beginTransaction()
                    .replace(R.id.step_detail_fragment_container, ingredientsFragment)
                    .commit();


        } else {
            Intent intent = new Intent(this, IngredientsActivity.class);
            intent.putExtra(getString(R.string.key_recipe), mRecipe);
            startActivity(intent);
        }

    }

    @Override
    public void onPreviousClicked() {

    }

    @Override
    public void onNextClicked() {

    }
}
