package com.udacity_developing_android.eiko.baking_app_project3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter
        <RecipeAdapter.RecipeAdapterViewHolder> {

    private static final String LOG = RecipeAdapter.class.getSimpleName();

    private ArrayList<Recipe> reipeArrayList;
    private RecipeAdapterOnClickHandler recipeAdapterOnClickHandler;
    private Context mContext;

    public RecipeAdapter(Context context, RecipeAdapterOnClickHandler handler) {
        this.mContext = context;
        this.recipeAdapterOnClickHandler = handler;
    }

    @Override
    public RecipeAdapter.RecipeAdapterViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        int listItemId = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(listItemId, parent, false);

        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            RecipeAdapter.RecipeAdapterViewHolder holder,
            int position) {
        String recipeName = reipeArrayList.get(position).getName();
        Log.i("RecipeAdapter LOG" ,recipeName);

        String recipeImgUrl = reipeArrayList.get(position).getImage();

        holder.recipeNameTextView.setText(recipeName);
         Log.v("recipe image URL: ", recipeImgUrl);

        if (recipeImgUrl.isEmpty()) {
            String imageResName = recipeName.replaceAll("\\s+", "")
                    .toLowerCase();
            int imageResId = mContext.getResources().getIdentifier(
                    imageResName, "drawable",
                    mContext.getPackageName());
            Log.i("image res ID: "+String.valueOf(imageResId),
                    "imageRes name"+imageResName);
//ID :0 , name Nutellapie
            Picasso.get()//Picasso.get(mContext)
                    .load(R.drawable.recipebook)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.recipeImageview);
        } else {
            Picasso.get()
                    .load(recipeImgUrl)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(holder.recipeImageview);
        }
    }

    @Override
    public int getItemCount() {
        if (reipeArrayList == null) {
            return 0;
        }
        return reipeArrayList.size();
    }

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public class RecipeAdapterViewHolder extends
            RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_imageview)
        ImageView recipeImageview;

        @BindView(R.id.recipe_image_name_textview)
        TextView recipeNameTextView;

        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Recipe recipeObject = reipeArrayList.get(position);
            recipeAdapterOnClickHandler.onClick(recipeObject);
        }
    }
    public void setRecipeData(ArrayList<Recipe> recipeData) {
        reipeArrayList = recipeData;
        notifyDataSetChanged();
    }
}
