package com.greymatter.heresthedrink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.greymatter.heresthedrink.model.Category;
import com.greymatter.heresthedrink.model.Drink;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    Drink drink;
    TextView name,desc,price;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);

        drink = new Gson().fromJson(getIntent().getStringExtra("drink"), Drink.class);

        name.setText(drink.getName());
        desc.setText(drink.getDescription());
        price.setText("₹"+drink.getPrice());

        Picasso.get().load(drink.getImage()).into(image);
    }

    public void openBlueprint(View view) {
        startActivity(new Intent(getApplicationContext(),BluePrintActivity.class)
                .putExtra("url",drink.getBlueprint())
        );
    }
}