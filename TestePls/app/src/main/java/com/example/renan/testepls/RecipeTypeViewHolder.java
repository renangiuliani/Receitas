package com.example.renan.testepls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;
import static android.support.v4.app.ActivityCompat.startActivity;


/**
 * Created by Renan on 17/09/2015.
 */
public class RecipeTypeViewHolder extends RecyclerView.ViewHolder{

    private Context context;
    public TextView tvName;
    public ImageView ivPicture;

    public RecipeTypeViewHolder(final Context context, final View itemView) {
        super(itemView);
        this.context = context;
        tvName = (TextView) itemView.findViewById(R.id.tvRecipeTypeName);
        ivPicture = (ImageView) itemView.findViewById(R.id.ivRecipeTypePicture);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Você clicou em " + tvName.getText().toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent((Activity) context, ListRecipeTypeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("recipeType", tvName.getText().toString());

                startActivity((Activity) context, intent, bundle);
            }
        });
    }
}
