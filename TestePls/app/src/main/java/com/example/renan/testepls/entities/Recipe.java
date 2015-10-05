package com.example.renan.testepls.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.renan.testepls.persistence.RecipeRepository;

import java.util.List;

/**
 * Created by c1284141 on 25/09/2015.
 */
public class Recipe implements Parcelable {

    private Integer id;
    private String title;
    private int imageRecipe;
    private List<String> ingredients;
    private String prepareMode;
    private String prepareTime;
    private int serves;
    private int recipeType;
    private String observation;

    public Recipe(){
        super();
    }

    public Integer getId(){
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

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
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

    public int getImageRecipe() {
        return imageRecipe;
    }

    public void setImageRecipe(int imageRecipe) {
        this.imageRecipe = imageRecipe;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void setAll(String title, int imageRecipe, String prepareMode, String prepareTime, int serves, int recipeType, String observation){
        this.title = title;
        this.imageRecipe = imageRecipe;
        this.prepareMode = prepareMode;
        this.prepareTime = prepareTime;
        this.serves = serves;
        this.recipeType = recipeType;
        this.observation = observation;
    }

    public static List<Recipe> getAll(){
        return RecipeRepository.getInstance().getAll();
    }

    public void save(){
        RecipeRepository.getInstance().save(this);
    }

    public void delete(int id){
        RecipeRepository.getInstance().delete(id);
    }

    public static List<Recipe> getByType(int codeType, int limit){
        return RecipeRepository.getInstance().getByType(codeType, limit);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeInt(this.imageRecipe);
        dest.writeStringList(this.ingredients);
        dest.writeString(this.prepareMode);
        dest.writeString(this.prepareTime);
        dest.writeInt(this.serves);
        dest.writeInt(this.recipeType);
        dest.writeString(this.observation);
    }

    protected Recipe(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.imageRecipe = in.readInt();
        this.ingredients = in.createStringArrayList();
        this.prepareMode = in.readString();
        this.prepareTime = in.readString();
        this.serves = in.readInt();
        this.recipeType = in.readInt();
        this.observation = in.readString();
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
