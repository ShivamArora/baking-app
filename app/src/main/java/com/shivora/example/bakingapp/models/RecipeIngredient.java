package com.shivora.example.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeIngredient implements Parcelable {
    private int quantity;
    private String measure;
    private String ingredient;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    //CREATOR
    public static final Parcelable.Creator<RecipeIngredient> CREATOR = new Parcelable.Creator<RecipeIngredient>(){

        @Override
        public RecipeIngredient createFromParcel(Parcel source) {
            return new RecipeIngredient(source);
        }

        @Override
        public RecipeIngredient[] newArray(int size) {
            return new RecipeIngredient[size];
        }
    };

    public RecipeIngredient(Parcel in){
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();
    }
}
