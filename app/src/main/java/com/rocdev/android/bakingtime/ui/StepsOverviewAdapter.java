package com.rocdev.android.bakingtime.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rocdev.android.bakingtime.R;
import com.rocdev.android.bakingtime.models.Step;

import java.util.List;

/**
 * Created by piet on 15-10-17.
 */

public class StepsOverviewAdapter extends RecyclerView.Adapter<StepsOverviewAdapter.ViewHolder> {


    private Context mContext;
    private OnItemClickedListener mCallback;
    private List<Step> mSteps;


    public StepsOverviewAdapter(Context context, OnItemClickedListener callback,
                                List<Step> steps) {
        this.mContext = context;
        this.mCallback = callback;
        this.mSteps = steps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View stepView = inflater.inflate(R.layout.step_list_item, parent, false);
        return new ViewHolder(stepView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            holder.textView.setText(R.string.ingredients);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onIngredientsClicked();
                }
            });
        } else {
            final Step step = mSteps.get(position);
            holder.textView.setText(step.getShortDescription());
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onStepClicked(step);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (mSteps == null) return 0;
        return mSteps.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View container;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.fl_step_container);
            textView = itemView.findViewById(R.id.tv_step_description);
        }
    }

    interface OnItemClickedListener {
        void onStepClicked(Step step);
        void onIngredientsClicked();
    }
}
