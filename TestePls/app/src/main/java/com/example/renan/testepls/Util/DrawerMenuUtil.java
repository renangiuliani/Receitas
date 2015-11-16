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
import com.example.renan.testepls.entities.Recipe;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.HashMap;

import static com.example.renan.testepls.entities.EnumRecipeType.getEnumByCode;

/**
 * Created by c1284141 on 06/11/2015.
 */
public class DrawerMenuUtil {

    private Context mContext;
    private Toolbar mToolbar;
    private Recipe recipe;

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
                new PrimaryDrawerItem().withName("Todos").withIdentifier(11).withIcon(R.drawable.all_drawer2).withBadge(String.valueOf(recipe.getAll(-1).size())),
                new PrimaryDrawerItem().withName("Favoritos").withIdentifier(12).withIcon(R.drawable.ic_favorite_on).withBadge(String.valueOf(recipe.getByType(-1, setValueHashMap("favorite", String.valueOf(1))).size())),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName(EnumRecipeType.MEAT.getName()).withIdentifier(EnumRecipeType.MEAT.getCode()).withIcon(EnumRecipeType.MEAT.getImage()).withBadge(String.valueOf(recipe.getByType(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.MEAT.getCode()))).size())),
                new SecondaryDrawerItem().withName(EnumRecipeType.BIRD.getName()).withIdentifier(EnumRecipeType.BIRD.getCode()).withIcon(EnumRecipeType.BIRD.getImage()).withBadge(String.valueOf(recipe.getByType(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.BIRD.getCode()))).size())),
                new SecondaryDrawerItem().withName(EnumRecipeType.FISH.getName()).withIdentifier(EnumRecipeType.FISH.getCode()).withIcon(EnumRecipeType.FISH.getImage()).withBadge(String.valueOf(recipe.getByType(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.FISH.getCode()))).size())),
                new SecondaryDrawerItem().withName(EnumRecipeType.PASTA.getName()).withIdentifier(EnumRecipeType.PASTA.getCode()).withIcon(EnumRecipeType.PASTA.getImage()).withBadge(String.valueOf(recipe.getByType(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.PASTA.getCode()))).size())),
                new SecondaryDrawerItem().withName(EnumRecipeType.SALAD.getName()).withIdentifier(EnumRecipeType.SALAD.getCode()).withIcon(EnumRecipeType.SALAD.getImage()).withBadge(String.valueOf(recipe.getByType(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.SALAD.getCode()))).size())),
                new SecondaryDrawerItem().withName(EnumRecipeType.SOUP.getName()).withIdentifier(EnumRecipeType.SOUP.getCode()).withIcon(EnumRecipeType.SOUP.getImage()).withBadge(String.valueOf(recipe.getByType(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.SOUP.getCode()))).size())),
                new SecondaryDrawerItem().withName(EnumRecipeType.BREAD.getName()).withIdentifier(EnumRecipeType.BREAD.getCode()).withIcon(EnumRecipeType.BREAD.getImage()).withBadge(String.valueOf(recipe.getByType(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.BREAD.getCode()))).size())),
                new SecondaryDrawerItem().withName(EnumRecipeType.CANDY.getName()).withIdentifier(EnumRecipeType.CANDY.getCode()).withIcon(EnumRecipeType.CANDY.getImage()).withBadge(String.valueOf(recipe.getByType(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.CANDY.getCode()))).size())),
                new SecondaryDrawerItem().withName(EnumRecipeType.DRINK.getName()).withIdentifier(EnumRecipeType.DRINK.getCode()).withIcon(EnumRecipeType.DRINK.getImage()).withBadge(String.valueOf(recipe.getByType(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.DRINK.getCode()))).size())),
                new SecondaryDrawerItem().withName(EnumRecipeType.SAUCE.getName()).withIdentifier(EnumRecipeType.SAUCE.getCode()).withIcon(EnumRecipeType.SAUCE.getImage()).withBadge(String.valueOf(recipe.getByType(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.SAUCE.getCode()))).size()))
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
                .withFullscreen(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 11) {
                            Toast.makeText(mContext, "Filtrar Todos", Toast.LENGTH_SHORT).show();
                        } else if (drawerItem.getIdentifier() == 12) {
                            Toast.makeText(mContext, "Filtrar Favoritos", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Filtrar " + getEnumByCode(drawerItem.getIdentifier()).getName(), Toast.LENGTH_SHORT).show();
                        }

                        ListRecipeActivity.filterRecipeType = drawerItem.getIdentifier();
                        ListRecipeActivity.updateItens(0);

                        return false;
                    }
                });
    }

    private HashMap<String, String> setValueHashMap(String key, String value){
        HashMap<String, String> hashMapCodeType = new HashMap<String, String>();

        hashMapCodeType.put(key, value);

        return hashMapCodeType;
    }
}