package com.example.renan.testepls.Util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.renan.testepls.R;
import com.example.renan.testepls.activities.ListRecipeActivity;
import com.example.renan.testepls.entities.EnumRecipeType;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import static com.example.renan.testepls.entities.EnumRecipeType.getEnumByCode;

/**
 * Created by c1284141 on 06/11/2015.
 */
public class DrawerMenuUtil {

    private Context mContext;
    private Toolbar mToolbar;

    public DrawerMenuUtil(Context context, Toolbar toolbar) {
        mContext = context;
        mToolbar = toolbar;
    }

    private AccountHeader buildHeader() {
        return new AccountHeaderBuilder()
                .withActivity((Activity) mContext)
                .withHeaderBackground(R.drawable.background_drawer)
                .withTranslucentStatusBar(true)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Receitas")
                                .withEmail("O melhor aplicativo de receitas do mundo.")
                                .withIcon(mContext.getResources()
                                                .getDrawable(R.mipmap.ic_app)
                                )
                )
                .build();
    }

    public DrawerBuilder create() {
        IDrawerItem[] drawerItens = new IDrawerItem[]{
                new PrimaryDrawerItem().withName("Todos").withIdentifier(11).withIcon(R.drawable.all_drawer2),
                new PrimaryDrawerItem().withName("Favoritos").withIdentifier(12).withIcon(R.drawable.ic_favorite_on),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName(EnumRecipeType.MEAT.getName()).withIdentifier(EnumRecipeType.MEAT.getCode()).withIcon(EnumRecipeType.MEAT.getImage()),
                new SecondaryDrawerItem().withName(EnumRecipeType.BIRD.getName()).withIdentifier(EnumRecipeType.BIRD.getCode()).withIcon(EnumRecipeType.MEAT.getImage()),
                new SecondaryDrawerItem().withName(EnumRecipeType.FISH.getName()).withIdentifier(EnumRecipeType.FISH.getCode()).withIcon(EnumRecipeType.FISH.getImage()),
                new SecondaryDrawerItem().withName(EnumRecipeType.PASTA.getName()).withIdentifier(EnumRecipeType.PASTA.getCode()).withIcon(EnumRecipeType.PASTA.getImage()),
                new SecondaryDrawerItem().withName(EnumRecipeType.SALAD.getName()).withIdentifier(EnumRecipeType.SALAD.getCode()).withIcon(EnumRecipeType.SALAD.getImage()),
                new SecondaryDrawerItem().withName(EnumRecipeType.SOUP.getName()).withIdentifier(EnumRecipeType.SOUP.getCode()).withIcon(EnumRecipeType.SOUP.getImage()),
                new SecondaryDrawerItem().withName(EnumRecipeType.BREAD.getName()).withIdentifier(EnumRecipeType.BREAD.getCode()).withIcon(EnumRecipeType.BREAD.getImage()),
                new SecondaryDrawerItem().withName(EnumRecipeType.CANDY.getName()).withIdentifier(EnumRecipeType.CANDY.getCode()).withIcon(EnumRecipeType.CANDY.getImage()),
                new SecondaryDrawerItem().withName(EnumRecipeType.DRINK.getName()).withIdentifier(EnumRecipeType.DRINK.getCode()).withIcon(EnumRecipeType.DRINK.getImage()),
                new SecondaryDrawerItem().withName(EnumRecipeType.SAUCE.getName()).withIdentifier(EnumRecipeType.SAUCE.getCode()).withIcon(EnumRecipeType.SAUCE.getImage())
        };

        return new DrawerBuilder()
                .withToolbar(mToolbar)
                .withActivity((Activity) mContext)
                .withAccountHeader(buildHeader())
                .withDisplayBelowToolbar(true)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(drawerItens)
                .withActionBarDrawerToggleAnimated(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if(drawerItem.getIdentifier() == 11){
                            Toast.makeText(mContext, "Filtrar Todos", Toast.LENGTH_SHORT).show();
                        }else if (drawerItem.getIdentifier() == 12) {
                            Toast.makeText(mContext, "Filtrar Favoritos", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, "Filtrar " + getEnumByCode(drawerItem.getIdentifier()).getName(), Toast.LENGTH_SHORT).show();
                        }

                        ListRecipeActivity.filterRecipeType = drawerItem.getIdentifier();
                        ListRecipeActivity.updateItens(0);
//                        switch (drawerItem.getIdentifier()){
//                            case 1:
//                                Toast.makeText(mContext, "Filtrar " + getEnumByCode(drawerItem.getIdentifier()).getName(), Toast.LENGTH_SHORT).show();
//                                ListRecipeActivity.filterRecipeType = drawerItem.getIdentifier();
//                                break;
//                            case 2:
//                                Toast.makeText(mContext, "Filtrar " + getEnumByCode(drawerItem.getIdentifier()).getName(), Toast.LENGTH_SHORT).show();
//                                ListRecipeActivity.filterRecipeType = drawerItem.getIdentifier();
//                                break;
//                            case 3:
//                                Toast.makeText(mContext, "Filtrar " + getEnumByCode(drawerItem.getIdentifier()).getName(), Toast.LENGTH_SHORT).show();
//                                ListRecipeActivity.filterRecipeType = drawerItem.getIdentifier();
//                                break;
//                            case 4:
//                                Toast.makeText(mContext, "Filtrar " + getEnumByCode(drawerItem.getIdentifier()).getName(), Toast.LENGTH_SHORT).show();
//                                ListRecipeActivity.filterRecipeType = drawerItem.getIdentifier();
//                                break;
//                            case 5:
//                                Toast.makeText(mContext, "Filtrar " + getEnumByCode(drawerItem.getIdentifier()).getName(), Toast.LENGTH_SHORT).show();
//                                break;
//                            case 6:
//                                Toast.makeText(mContext, "Filtrar " + getEnumByCode(drawerItem.getIdentifier()).getName(), Toast.LENGTH_SHORT).show();
//                                break;
//                            case 7:
//                                Toast.makeText(mContext, "Filtrar " + getEnumByCode(drawerItem.getIdentifier()).getName(), Toast.LENGTH_SHORT).show();
//                                break;
//                            case 8:
//                                Toast.makeText(mContext, "Filtrar " + getEnumByCode(drawerItem.getIdentifier()).getName(), Toast.LENGTH_SHORT).show();
//                                break;
//                            case 9:
//                                Toast.makeText(mContext, "Filtrar " + getEnumByCode(drawerItem.getIdentifier()).getName(), Toast.LENGTH_SHORT).show();
//                                break;
//                            case 10:
//                                Toast.makeText(mContext, "Filtrar " + getEnumByCode(drawerItem.getIdentifier()).getName(), Toast.LENGTH_SHORT).show();
//                                break;
//                            case 11:
//                                Toast.makeText(mContext, "Filtrar Todos", Toast.LENGTH_SHORT).show();
//                                break;
//                            case 12:
//                                Toast.makeText(mContext, "Filtrar Favoritos", Toast.LENGTH_SHORT).show();
//                                break;
//                        }
                        return false;
                    }
                });

    }
}