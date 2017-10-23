package com.rocdev.android.bakingtime.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.rocdev.android.bakingtime.R;

/**
 * Created by piet on 14-10-17.
 */

public class Ingredient implements Parcelable {


    private String name;
    private String measure;
    private double quantity;

    public Ingredient(String name, String measure, double quantity) {
        this.name = name;
        this.measure = measure;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public double getQuantity() {
        return quantity;
    }
    
    
    
    public static String getNormalMeasure(Context context, String measure) {
        switch (measure) {
            case "CUP":
                return context.getString(R.string.cups);
            case "TBLSP":
                return context.getString(R.string.tablespoon);
            case "TSP":
                return context.getString(R.string.teaspoon);
            case "K":
                return context.getString(R.string.kilogram);
            case "G":
                return context.getString(R.string.gram);
            case "OZ":
                return context.getString(R.string.ounce);
            case "UNIT":
                return context.getString(R.string.unit);
            default:
                return "";
        }
    }

    protected Ingredient(Parcel in) {
        name = in.readString();
        measure = in.readString();
        quantity = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(measure);
        dest.writeDouble(quantity);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}