package com.udacity_developing_android.eiko.baking_app_project3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<
        IngredientAdapter.IngredientViewHolder> {

    private Context mContext;
    private ArrayList<String> ingredientList;

    public IngredientAdapter(Context context, ArrayList<String> list) {
        mContext = context;
        ingredientList = list;
    }

    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        Context parentContex = parent.getContext();
        int layoutId = R.layout.recipe_ingredient_item;
        LayoutInflater inflater = LayoutInflater.from(parentContex);
        boolean shouldAttach = false;
        View view = inflater.inflate(layoutId, parent, shouldAttach);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            IngredientAdapter.IngredientViewHolder holder, int position) {
        String indredientInfo = ingredientList.get(position);
        holder.ingredientItem.setText(indredientInfo);
    }

    @Override
    public int getItemCount() {
        if (ingredientList == null) {
            return 0;
        }
        return ingredientList.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientItem;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
