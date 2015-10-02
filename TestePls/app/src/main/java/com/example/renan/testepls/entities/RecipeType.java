package com.example.renan.testepls.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Renan on 17/09/2015.
 */
public class RecipeType implements Parcelable {

    private int imageRecipeType;
    private EnumRecipeType enumRecipeType;


    public RecipeType(int imageRecipeType, EnumRecipeType enumRecipeType) {
        this.imageRecipeType = imageRecipeType;
        this.enumRecipeType = enumRecipeType;
    }

    public int getImageRecipeType(){
        return imageRecipeType;
    }

    public EnumRecipeType getEnumRecipeType(){
        return enumRecipeType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.imageRecipeType);
        dest.writeInt(this.enumRecipeType == null ? -1 : this.enumRecipeType.ordinal());
    }

    protected RecipeType(Parcel in) {
        this.imageRecipeType = in.readInt();
        int tmpEnumRecipeType = in.readInt();
        this.enumRecipeType = tmpEnumRecipeType == -1 ? null : EnumRecipeType.values()[tmpEnumRecipeType];
    }

    public static final Parcelable.Creator<RecipeType> CREATOR = new Parcelable.Creator<RecipeType>() {
        public RecipeType createFromParcel(Parcel source) {
            return new RecipeType(source);
        }

        public RecipeType[] newArray(int size) {
            return new RecipeType[size];
        }
    };
}
