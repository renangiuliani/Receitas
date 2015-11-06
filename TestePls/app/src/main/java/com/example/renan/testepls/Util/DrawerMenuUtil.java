package com.example.renan.testepls.Util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;

import com.example.renan.testepls.R;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

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
                new PrimaryDrawerItem().withName("Todos").withIdentifier(1).withIcon(R.drawable.all_drawer2),
                new PrimaryDrawerItem().withName("Favoritos").withIdentifier(2).withIcon(R.drawable.ic_favorite_on),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName("Carnes").withIdentifier(3).withIcon(R.drawable.meat),
                new SecondaryDrawerItem().withName("Aves").withIdentifier(4).withIcon(R.drawable.bird),
                new SecondaryDrawerItem().withName("Peixes e Frutos do Mar").withIdentifier(5).withIcon(R.drawable.fish),
                new SecondaryDrawerItem().withName("Massas").withIdentifier(6).withIcon(R.drawable.pasta),
                new SecondaryDrawerItem().withName("Saladas").withIdentifier(7).withIcon(R.drawable.salad),
                new SecondaryDrawerItem().withName("Sopas").withIdentifier(8).withIcon(R.drawable.soup),
                new SecondaryDrawerItem().withName("Pães e Sanduíches").withIdentifier(9).withIcon(R.drawable.bread),
                new SecondaryDrawerItem().withName("Doces e Sobremesas").withIdentifier(10).withIcon(R.drawable.candy),
                new SecondaryDrawerItem().withName("Bebidas").withIdentifier(11).withIcon(R.drawable.drink),
                new SecondaryDrawerItem().withName("Molhos e Acompanhamentos").withIdentifier(12).withIcon(R.drawable.sauce)
        };

        return new DrawerBuilder()
                .withToolbar(mToolbar)
                .withActivity((Activity) mContext)
                .withAccountHeader(buildHeader())
                .withDisplayBelowToolbar(true)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(drawerItens)
                .withActionBarDrawerToggleAnimated(true);

    }
}