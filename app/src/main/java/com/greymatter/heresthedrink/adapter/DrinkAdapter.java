package com.greymatter.heresthedrink.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.greymatter.heresthedrink.ProductDetailActivity;
import com.greymatter.heresthedrink.ProductListActivity;
import com.greymatter.heresthedrink.R;
import com.greymatter.heresthedrink.model.Category;
import com.greymatter.heresthedrink.model.Drink;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder>{

    Context context;
    List<Drink> drinkList;

    public DrinkAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    public void updateList(List<Drink> temp) {
        drinkList = temp;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Drink model = drinkList.get(position);

        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice()+"kr");

        Picasso.get().load(model.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String drinkJson = new Gson().toJson(model);

                Intent i = new Intent(context, ProductDetailActivity.class);
                i.putExtra("drink",drinkJson);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,price;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            image = itemView.findViewById(R.id.imageView);
        }
    }

}