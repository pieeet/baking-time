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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(getString(R.string.key_recipe));
        StepsFragment fragment = StepsFragment.newInstance(recipe);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.steps_fragment_container, fragment)
                .commit();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(recipe.getName());
        }
    }

    @Override
    public void onStepClicked(Step step) {
        Toast.makeText(this, "Step name is: " + step.getShortDescription(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onIngredientsClicked() {
        Toast.makeText(this, "OnIngredientsClicked triggered", Toast.LENGTH_SHORT).show();
    }
}
