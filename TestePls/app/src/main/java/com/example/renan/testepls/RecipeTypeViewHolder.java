package com.example.renan.testepls;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.app.PendingIntent.getActivity;


/**
 * Created by Renan on 17/09/2015.
 */
public class RecipeTypeViewHolder extends RecyclerView.ViewHolder{

    public TextView tvName;
    public ImageView ivPicture;

    public RecipeTypeViewHolder(final View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_recipe_type_name);
        ivPicture = (ImageView) itemView.findViewById(R.id.iv_recipe_type_picture);
    }
}
