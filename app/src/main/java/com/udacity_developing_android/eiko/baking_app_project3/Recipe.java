package com.udacity_developing_android.eiko.baking_app_project3;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    private String id, name, servings, image;
    private ArrayList<String> ingredient;
    private ArrayList<RecipeStep> steps;

    public Recipe(){
        super();
    }

    public Recipe(String id, String name, String serving,
                  String image, ArrayList<String> ingredient,
                  ArrayList<RecipeStep> steps){
        this.id = id;
        this.name = name;
        this.servings = serving;
        this.image = image;
        this.ingredient = ingredient;
        this.steps = steps;
    }

    protected Recipe(Parcel in) {
        id = in.readString();
        name = in.readString();
        servings = in.readString();
        image = in.readString();
        ingredient = in.createStringArrayList();
        steps = in.createTypedArrayList(RecipeStep.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(servings);
        dest.writeString(image);
        dest.writeStringList(ingredient);
        dest.writeTypedList(steps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServing() {
        return servings;
    }

    public void setServing(String serving) {
        this.servings = serving;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getIngredient() {
        return ingredient;
    }

    public void setIngredient(ArrayList<String> ingredient) {
        this.ingredient = ingredient;
    }

    public ArrayList<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<RecipeStep> steps) {
        this.steps = steps;
    }
}
