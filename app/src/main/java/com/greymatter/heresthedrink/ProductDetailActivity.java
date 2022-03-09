package com.greymatter.heresthedrink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.greymatter.heresthedrink.model.Drink;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    Drink drink;
    TextView name,desc,price,status;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);
        status = findViewById(R.id.status);

        drink = new Gson().fromJson(getIntent().getStringExtra("drink"), Drink.class);

        name.setText(drink.getName());
        desc.setText(drink.getDescription());
        price.setText(drink.getPrice() + "kr");

        Picasso.get().load(drink.getImage()).into(image);

        if (drink.isStock_status()){
            status.setText("Available");
            status.setTextColor(getResources().getColor(R.color.green));
        }else {
            status.setText("Not Available");
            status.setTextColor(getResources().getColor(R.color.red));
        }

    }

    public void openBlueprint(View view) {
        startActivity(new Intent(getApplicationContext(),BluePrintActivity.class)
                .putExtra("url",drink.getBlueprint())
        );
    }

    public void back_btn(View view) {
        onBackPressed();
    }
}