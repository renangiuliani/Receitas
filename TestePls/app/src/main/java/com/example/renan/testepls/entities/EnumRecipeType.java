package com.example.renan.testepls.entities;

import com.example.renan.testepls.R;

/**
 * Created by c1284141 on 02/10/2015.
 */
public enum EnumRecipeType {
    MEAT(1, "Carnes", R.drawable.meat),
    BIRD(2, "Aves", R.drawable.bird),
    FISH(3, "Peixes e Frutos do Mar", R.drawable.fish),
    PASTA(4, "Massas", R.drawable.pasta),
    SALAD(5, "Saladas", R.drawable.salad),
    SOUP(6, "Sopas", R.drawable.soup),
    BREAD(7, "Pães e Sanduíches", R.drawable.bread),
    CANDY(8, "Doces e Sobremesas", R.drawable.candy),
    DRINK(9, "Bebidas", R.drawable.bread),
    SAUCE(10, "Molhos e Acompanhamentos", R.drawable.sauce);

    private String name;
    private int code;
    private int image;

    EnumRecipeType(int code, String name, int image){
        this.code = code;
        this.name = name;
        this.image = image;
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
