package com.example.renan.recipeapplication.entities;

import com.example.renan.recipeapplication.R;

/**
 * Created by c1284141 on 02/10/2015.
 */
public enum EnumRecipeType {
    MEAT(1, "Carnes", R.drawable.meat, R.drawable.meat_black_white),
    BIRD(2, "Aves", R.drawable.bird, R.drawable.bird_black_white),
    FISH(3, "Peixes e Frutos do Mar", R.drawable.fish, R.drawable.fish_black_white),
    PASTA(4, "Massas", R.drawable.pasta, R.drawable.pasta_black_white),
    SALAD(5, "Saladas", R.drawable.salad, R.drawable.salad_black_white),
    SOUP(6, "Sopas", R.drawable.soup, R.drawable.soup_black_white),
    BREAD(7, "Pães e Sanduíches", R.drawable.bread, R.drawable.bread_black_white),
    CANDY(8, "Doces e Sobremesas", R.drawable.candy, R.drawable.candy_black_white),
    DRINK(9, "Bebidas", R.drawable.drink, R.drawable.drink_black_white),
    SAUCE(10, "Molhos e Acompanhamentos", R.drawable.sauce, R.drawable.sauce_black_white);

    private String name;
    private int code;
    private int image;
    private int imageBlackWhite;

    EnumRecipeType(int code, String name, int image, int image_black_white){
        this.code = code;
        this.name = name;
        this.image = image;
        this.imageBlackWhite = image_black_white;
    }

    public String getName(){
        return this.name;
    }

    public int getCode(){
        return this.code;
    }

    public int getImage() {
        return image;
    }

    public int getImageBlackWhite() {
        return imageBlackWhite;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static EnumRecipeType getEnumByCode(int value) {
        for(EnumRecipeType e: EnumRecipeType.values()) {
            if(e.getCode() == value) {
                return e;
            }
        }
        return null;// not found
    }
}
