package com.udacity_developing_android.eiko.baking_app_project3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<
        StepAdapter.StepViewHolder> {

    private final int STEP = 1;
    private Context mContext;
    private ArrayList<RecipeStep> stepList;
    private StepAdapterOnclicklistner stepAdapterOnclicklistner;

    public StepAdapter(Context context, ArrayList<RecipeStep> recipeSteps,
                       StepAdapterOnclicklistner listner) {
        mContext = context;
        stepList = recipeSteps;
        stepAdapterOnclicklistner = listner;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parentContext);
        int layoutId = R.layout.recipe_step_item;
        boolean shoukdAttach = false;
        View view = inflater.inflate(layoutId, parent, shoukdAttach);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case STEP:
                RecipeStep currentStep = stepList.get(position);
                holder.stepId.setText("Step " + (position + 1));
                holder.stepDescripton.setText(
                        currentStep.getShortDescription());
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (stepList == null) {
            return 0;
        }
        return stepList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (stepList.get(position) != null) {
            return STEP;
        }
        return -1;
    }

    public interface StepAdapterOnclicklistner {
        void onClick(RecipeStep currentStep);
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        TextView stepId;
        TextView stepDescripton;

        public StepViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            RecipeStep recipeStep = stepList.get(position);
            stepAdapterOnclicklistner.onClick(recipeStep);
        }
    }
}
