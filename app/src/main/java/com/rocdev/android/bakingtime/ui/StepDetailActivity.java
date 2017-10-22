package com.rocdev.android.bakingtime.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rocdev.android.bakingtime.R;
import com.rocdev.android.bakingtime.models.Recipe;

public class StepDetailActivity extends AppCompatActivity implements
        StepDetailFragment.OnFragmentInteractionListener {

    private Recipe mRecipe;
    private int mStepPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra(getString(R.string.key_recipe));
        mStepPosition = intent.getIntExtra(getString(R.string.key_step_position), 0);
        setActionBarTitle();
        setFragment();
    }

    private void setFragment() {
        Fragment stepDetailFragment = StepDetailFragment.newInstance(mRecipe, mStepPosition);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.step_detail_fragment_container, stepDetailFragment)
                .commit();
    }

    private void setActionBarTitle() {
        if (getSupportActionBar() != null) {
            String stepName;
            if (mStepPosition == 0) {
                stepName = " - intro";
            } else {
                stepName = " step " + mStepPosition;
            }
            getSupportActionBar().setTitle(mRecipe.getName() + stepName);
        }
    }


    @Override
    public void onPreviousClicked() {
        mStepPosition--;
        setActionBarTitle();
        setFragment();
    }

    @Override
    public void onNextClicked() {
        mStepPosition++;
        setActionBarTitle();
        setFragment();
    }
}
