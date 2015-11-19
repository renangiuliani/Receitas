package com.example.renan.testepls.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.renan.testepls.persistence.RecipeRepository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by c1284141 on 25/09/2015.
 */
public class Recipe implements Parcelable {

    private Integer id;
    private String title;
    private byte[] imageRecipe;
    private String prepareMode;
    private String prepareTime;
    private int serves;
    private int recipeType;
    private String observation;
    private int favorite;
    private float price;
    private int difficulty;

    public Recipe() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrepareMode() {
        return prepareMode;
    }

    public void setPrepareMode(String prepareMode) {
        this.prepareMode = prepareMode;
    }

    public String getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(String prepareTime) {
        this.prepareTime = prepareTime;
    }

    public int getServes() {
        return serves;
    }

    public void setServes(int serves) {
        this.serves = serves;
    }

    public int getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(int recipeType) {
        this.recipeType = recipeType;
    }

    public byte[] getImageRecipe() {
        return imageRecipe;
    }

    public void setImageRecipe(byte[] imageRecipe) {
        this.imageRecipe = imageRecipe;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public static List<Recipe> getAll(int limit) {
        return RecipeRepository.getInstance().getAll(limit);
    }

    public long save() {
        return RecipeRepository.getInstance().save(this);
    }

    public void delete(int id) {
        RecipeRepository.getInstance().delete(id);
    }

    public static List<Recipe> getByType(int limit, HashMap<String, String> query) {
        return RecipeRepository.getInstance().getByType(limit, query);
    }

    public static Recipe getById(int idRecipe) {
        return RecipeRepository.getInstance().getById(idRecipe);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeByteArray(this.imageRecipe);
        dest.writeString(this.prepareMode);
        dest.writeString(this.prepareTime);
        dest.writeInt(this.serves);
        dest.writeInt(this.recipeType);
        dest.writeString(this.observation);
        dest.writeInt(this.favorite);
        dest.writeFloat(this.price);
        dest.writeInt(this.difficulty);
    }

    protected Recipe(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.imageRecipe = in.createByteArray();
        this.prepareMode = in.readString();
        this.prepareTime = in.readString();
        this.serves = in.readInt();
        this.recipeType = in.readInt();
        this.observation = in.readString();
        this.favorite = in.readInt();
        this.price = in.readFloat();
        this.difficulty = in.readInt();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
