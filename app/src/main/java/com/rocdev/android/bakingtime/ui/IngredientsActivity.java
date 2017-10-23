package com.rocdev.android.bakingtime.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rocdev.android.bakingtime.R;
import com.rocdev.android.bakingtime.models.Recipe;

public class IngredientsActivity extends AppCompatActivity {

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        mRecipe = getIntent().getParcelableExtra(getString(R.string.key_recipe));
        initNavigation();
        initFragments();
    }


    private void initNavigation() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mRecipe.getName() + " - " +
                    getString(R.string.ingredients));
        }
    }

    private void initFragments() {
        IngredientsFragment fragment = IngredientsFragment.newInstance(mRecipe);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.ingredients_fragment_container, fragment)
                .commit();
    }
}
