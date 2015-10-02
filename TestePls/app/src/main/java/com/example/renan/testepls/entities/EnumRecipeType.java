package com.example.renan.testepls.entities;

/**
 * Created by c1284141 on 02/10/2015.
 */
public enum EnumRecipeType {
    MEAT(1, "Carnes"),
    BIRD(2, "Aves"),
    FISH(3, "Peixes e Frutos do Mar"),
    SALAD(4, "Saladas"),
    SAUCE(5, "Molhos e Acompanhamentos"),
    SOUP(6, "Sopas"),
    PASTA(7, "Massas"),
    DRINK(8, "Bebidas"),
    CANDY(9, "Doces e Sobremesas"),
    SANDWICH(10, "Lanches");

    private String name;
    private int code;

    EnumRecipeType(int code, String name){
        this.code = code;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getCode(){
        return this.code;
    }

    public EnumRecipeType getEnumByCode(int value) {
        for(EnumRecipeType e: EnumRecipeType.values()) {
            if(e.getCode() == value) {
                return e;
            }
        }
        return null;// not found
    }
}
