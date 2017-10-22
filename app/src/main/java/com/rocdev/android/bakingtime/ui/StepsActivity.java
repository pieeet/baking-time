package com.rocdev.android.bakingtime.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rocdev.android.bakingtime.R;
import com.rocdev.android.bakingtime.models.Recipe;
import com.rocdev.android.bakingtime.models.Step;

public class StepsActivity extends AppCompatActivity implements
        StepsAdapter.OnItemClickedListener {

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        mRecipe = getIntent().getParcelableExtra(getString(R.string.key_recipe));
        initNavigation();
        initfragments();
    }

    private void initfragments() {
        StepsFragment fragment = StepsFragment.newInstance(mRecipe);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.steps_fragment_container, fragment)
                .commit();
    }

    private void initNavigation() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mRecipe.getName());
        }
    }

    @Override
    public void onStepClicked(Step step) {
        Intent intent = new Intent(this, StepDetailActivity.class);
        intent.putExtra(getString(R.string.key_recipe), mRecipe);
        intent.putExtra(getString(R.string.key_step_position), getStepPosition(step));
        startActivity(intent);
    }

    private int getStepPosition(Step step) {
        for (int i = 0; i < mRecipe.getSteps().size(); i++) {
            if (step.getId() == mRecipe.getSteps().get(i).getId() ) return i;
        }
        return 0;
    }

    @Override
    public void onIngredientsClicked() {
        Toast.makeText(this, "OnIngredientsClicked triggered", Toast.LENGTH_SHORT).show();
    }
}
