package com.udacity_developing_android.eiko.baking_app_project3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailActivity extends AppCompatActivity {

    @BindView(R.id.step_description)
    TextView stepDetailDescription;
    @BindView(R.id.step_buton)
    Button stepButton;

    private RecipeStep currentStep;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_step_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        currentStep = extras.getParcelable(
                "CURRENT_RECIPE_STEP");
        Log.i("RecipeStep A: ", currentStep.toString() );
        int currentOrientation = getResources()
                .getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            stepDetailDescription.setVisibility(View.GONE);
            stepButton.setVisibility(View.GONE);
        } else {
            stepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String stepId = String.valueOf(
                            Integer.parseInt(currentStep.getStepId()));
                    RecipeStep stepNavigation =
                            RecipeDetailActivity.navigateStep(stepId);
                    if (stepNavigation != null) {
                        Bundle extras = new Bundle();
                        extras.putParcelable("CURRENT_RECIPE_STEP", stepNavigation);
                        Intent stepDetailActivityIntent = new Intent(
                                RecipeStepDetailActivity.this,
                                RecipeStepDetailActivity.class);
                        stepDetailActivityIntent.putExtras(extras);
                        startActivity(stepDetailActivityIntent);
                    } else {
                        Toast.makeText(RecipeStepDetailActivity.this,
                                "StepActivity", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public RecipeStep getCurrentStep() {
        return getIntent().getExtras().getParcelable(
                "CURRENT_RECIPE_STEP");
    }
}
