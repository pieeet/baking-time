package com.rocdev.android.bakingtime.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

import com.rocdev.android.bakingtime.R;

/**
 * Created by piet on 28-10-17.
 *
 */

public class WidgetConfigActivity extends Activity {

    TextView tvTitle;
    RecyclerView rvRecipes;
    int widgetId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_config);
        tvTitle = findViewById(R.id.widget_config_tv_title);
        rvRecipes = findViewById(R.id.widget_config_rv_recipes);
        widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
        }
        if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {


        } else {
            finish();
        }



    }
}
